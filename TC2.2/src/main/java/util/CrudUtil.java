package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.metadata.ClassMetadata;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ton
 */
public class CrudUtil {

    SessionFactory factory;
    Session sessao;

    public CrudUtil() {
    }

    private void inicia_sessao(Class classe) {
        try {
            AnnotationConfiguration config = new AnnotationConfiguration();
            config.configure("hibernate.cfg.xml");
            config.addAnnotatedClass(classe);
            factory = config.buildSessionFactory();
            sessao = factory.openSession();
            sessao.beginTransaction();
        } catch (HibernateException e) {
            System.out.println("Criação da sessão falhou. " + e);
        }
    }

    private void encerra_sessao() {
        sessao.close();
        factory.close();
    }

    public String exclui(Object registro) {
        String saida;

        inicia_sessao(registro.getClass());
        ClassMetadata metadata = sessao.getSessionFactory().getClassMetadata(registro.getClass());
        Object teste = sessao.get(registro.getClass(), metadata.getIdentifier(registro));

        if (teste == null) {
            encerra_sessao();
            return "Registro não encontrado";
        }

        try {
            sessao.delete(teste);
            sessao.getTransaction().commit();
            sessao.flush();
            saida = "Regitro excluido com sucesso";
        } catch (RuntimeException e) {
            if (sessao.getTransaction() != null) {
                sessao.getTransaction().rollback();
            }
            saida = e.toString();
        } finally {
            encerra_sessao();
        }
        return saida;
    }

    public String altera(Object registro) {
        String saida;

        inicia_sessao(registro.getClass());
        ClassMetadata metadata = sessao.getSessionFactory().getClassMetadata(registro.getClass());
        Object teste = sessao.get(registro.getClass(), metadata.getIdentifier(registro));

        if (teste == null) {
            encerra_sessao();
            return "Registro não encontrado";
        }

        try {
            sessao.merge(registro);
            sessao.getTransaction().commit();
            sessao.flush();
            saida = "Regitro alterado com sucesso";
        } catch (RuntimeException e) {
            if (sessao.getTransaction() != null) {
                sessao.getTransaction().rollback();
            }
            saida = e.toString();
        } finally {
            encerra_sessao();
        }
        return saida;
    }

    public String insere(Object registro) {
        String saida;
        inicia_sessao(registro.getClass());

        try {
            sessao.save(registro);
            sessao.getTransaction().commit();
            sessao.flush();
            saida = "Regitro gravado com sucesso";
        } catch (RuntimeException e) {
            if (sessao.getTransaction() != null) {
                sessao.getTransaction().rollback();
            }
            String razao = e.toString();
            if (razao.contains("null")) {
                razao = razao.replaceAll(".*\\.", "") + " não pode estar vazio";
            } else {
                System.out.println(razao);
                razao = "Campos únicos duplicados.";
            }
            saida = razao;
        } finally {
            encerra_sessao();
        }
        return saida;
    }

    public List<Object> lista(Object origem) {
        List<Object> lista = null;

        inicia_sessao(origem.getClass());

        try {
            Class classe = origem.getClass();
            Method toQuery = classe.getDeclaredMethod("toQuery");
            String namedQuery = (String) toQuery.invoke(origem);
            lista = sessao.createQuery(namedQuery).list();
            sessao.flush();
            if (lista == null) {
                lista = new ArrayList();
                lista.add("Registro não encontrado");
            }
            sessao.flush();
        } catch (HibernateException e) {
            lista = new ArrayList();
            lista.add("Registro não encontrado");
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(CrudUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            encerra_sessao();
        }
        return lista;
    }

}
