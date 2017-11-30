package util;

import java.util.List;

import java.sql.Connection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.InvocationTargetException;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.persistence.EntityTransaction;

public class Dao {

    private Connection conexao;
    private static DatabaseMetaData metaData;

    private static final String UNIDADE = "TC2PU";

    private static Dao instance;
    private static EntityTransaction transacao;
    protected EntityManager entidadeM;

    public static Dao getInstance() {
        if (instance == null) {
            instance = new Dao();
        }
        return instance;
    }

    private Dao() {
        entidadeM = getEntidadeM();
    }

    private EntityManager getEntidadeM() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(UNIDADE);
        if (entidadeM == null) {
            entidadeM = factory.createEntityManager();
        }
        return entidadeM;
    }

    public Object buscaPorId(Object objeto) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return entidadeM.find(objeto.getClass(),
                (int) objeto.getClass().getMethod("getId").invoke(objeto));
    }

    public boolean existe(Object objeto) {
        String classe = objeto.getClass().getSimpleName().toLowerCase();
        String query = "select tablename from pg_catalog.pg_tables where tablename = '" + classe + "'";

        for (Object t : entidadeM.createNativeQuery(query).getResultList()) {
            return true;
        }
        return false;
    }

    public String desespero(Object objeto) throws SQLException {
        String classe = objeto.getClass().getSimpleName().toLowerCase();
        String query = "select tablename from pg_catalog.pg_tables where tablename = '" + classe + "'";

        for (Object t : entidadeM.createNativeQuery(query).getResultList()) {
            return " ";
        }
        return " não ";
    }

    public List<Object> busca(Object objeto, int nivel) {
        if (existe(objeto)) {
            return entidadeM.createNamedQuery(objeto.getClass().getSimpleName() + "." + nivel).getResultList();
        }
        return null;
    }

    @SuppressWarnings("unchecked")

    public String insere(Object objeto) {
        try {
            entidadeM.getTransaction().begin();
            entidadeM.persist(objeto);
            entidadeM.getTransaction().commit();
            return "Inserido com sucesso";
        } catch (Exception ex) {
            if (entidadeM.getTransaction().isActive()) {
                entidadeM.getTransaction().rollback();
            }
            String mensagem = ex.getCause().getCause().getCause().getMessage();
            String[] campo = mensagem
                    .substring(mensagem.indexOf(" (") + 1, mensagem.indexOf(") ") + 1)
                    .split("=");
            campo[0] = campo[0].replaceAll("\\(|\\)", "");
            return "O " + campo[0] + " " + campo[1] + " já está registrado no sistema;";
        }
    }

    public String altera(Object objeto) {
        try {
            entidadeM.getTransaction().begin();
            entidadeM.merge(objeto);
            entidadeM.getTransaction().commit();
            return "Alterado com sucesso";
        } catch (Exception ex) {
            if (entidadeM.getTransaction().isActive()) {
                entidadeM.getTransaction().rollback();
            }
            String mensagem = ex.getCause().getCause().getCause().getMessage();
            String[] campo = mensagem
                    .substring(mensagem.indexOf(" (") + 1, mensagem.indexOf(") ") + 1)
                    .split("=");
            campo[0] = campo[0].replaceAll("\\(|\\)", "");
            return "O " + campo[0] + " " + campo[1] + " já está registrado no sistema;";
        }
    }

    public String remove(Object objeto) {
        transacao = entidadeM.getTransaction();

        try {
            Object aRemover = buscaPorId(objeto);
            transacao.begin();
            entidadeM.remove(aRemover);
            transacao.commit();
            return "Removido com sucesso";
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException ex) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            return "Registro não excluido";
        }
    }
}
