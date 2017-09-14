package control;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Professor;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ton
 */
public class ProfessorControl {

    SessionFactory factory;
    Session sessao;

    public ProfessorControl() {
    }

    private void inicia_sessao() {
        try {
            factory = new HibernateUtil().getFactory();
            sessao = factory.openSession();
            sessao.beginTransaction();
        } catch (HibernateException e) {
            System.out.println("Erro ao abrir a sessão.");
        }
    }

    private void encerra_sessao() {
        sessao.close();
        factory.close();
    }

    public Professor getRegistro(Object origem) {
        String tabela = origem.getClass().getSimpleName();
        Field[] lista_campo;
        String campos = null;
        Field campo;
        int id = 0;
        Professor professor = null;

        lista_campo = origem.getClass().getDeclaredFields();

        for (Field campo_x : lista_campo) {
            campo_x.setAccessible(true);
            String campo_nome = campo_x.getName().toLowerCase();
            Object campo_valor = null;

            try {
                campo_valor = campo_x.get(origem);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
            }

            if (campo_valor != null) {
                campos = " where " + campo_nome;
                if (campo_nome.contains("id")) {
                    campos += "=" + campo_valor;
                } else if (campo_x.getType().getSimpleName().toLowerCase().contains("int")) {
                    campos += " like '%" + campo_valor + "%'";
                }

            }

            if (campo_nome.contains("id")) {
                try {
                    if (campo_x.get(origem) != null) {
                        campos = " where " + campo_nome + "=" + campo_x.get(origem);
                        break;
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                }
            } else {
                try {
                    if (campo_x.get(origem) != null) {
                        if (campo_x.getType().getSimpleName().toLowerCase().contains("int")) {
                            campos = " where " + campo_nome + "=" + campo_x.get(origem);
                        } else {
                            campos = " where " + campo_nome + " like '%" + campo_x.get(origem) + "%'";
                        }
                        break;
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                }
            }
        }

        try {
            campo = origem.getClass().getDeclaredField("id");
            campo.setAccessible(true);
            id = (Integer) campo.get(origem);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
        }

        inicia_sessao();

        try {
            Query query = sessao.createQuery("from " + tabela + " where id = " + id);
            professor = (Professor) query.uniqueResult();
            sessao.flush();
            if (professor == null) {
                professor = new Professor(0, "Registro não encontrado");
            }
        } catch (HibernateException e) {
            professor = new Professor(0, "Registro não encontrado");
        } finally {
            encerra_sessao();
        }
        return professor;
    }

    public List<Object> getLista(Object registro) {
        List<Object> lista = null;

        inicia_sessao();

        try {
            Query query = sessao.createQuery("from " + registro.getClass().getSimpleName());
            lista = query.list();
            if (lista == null) {
                lista = new ArrayList();
                lista.add("Registro não encontrado");
            }
            sessao.flush();
        } catch (HibernateException e) {
            lista = new ArrayList();
            lista.add("Registro não encontrado");
        } finally {
            encerra_sessao();
        }
        return lista;
    }

    public String insert(Object registro) {
        String saida;
        inicia_sessao();

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
                razao = "Campos únicos duplicados.";
            }
            saida = razao;
        } finally {
            encerra_sessao();
        }
        return saida;
    }

}
