package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

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
            factory = new AnnotationConfiguration().configure().buildSessionFactory();
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

    public List<Object> getRegistro(Object origem) {
        String tabela = origem.getClass().getSimpleName();
        Field[] lista_campo;
        String campos = null;
        Field campo;
        String tipo = null;
        int id = 0;
        List<Object> lista = null;

        lista_campo = origem.getClass().getDeclaredFields();

        for (Field campo_x : lista_campo) {
            campo_x.setAccessible(true);
            String campo_nome = campo_x.getName().toLowerCase();
            String campo_valor;

            try {
                campo_valor = campo_x.get(origem).toString();
                tipo = campo_x.getType().getSimpleName().toLowerCase();

                if (campo_valor != null) {
                    if (campo_nome.contains("id")) {
                        if (Integer.parseInt(campo_valor) != 0) {
                            if (campos == null) {
                                campos = " where " + campo_nome + " = " + campo_valor;
                                break;
                            } else {
                                campos += " and " + campo_nome + " like '%" + campo_valor + "%'";
                            }
                        }
                    } else {
                        if (campos == null) {
                            campos = " where lower(" + campo_nome + ") like '%" + campo_valor + "%'";
                        } else {
                            campos += " and lower(" + campo_nome + ") like '%" + campo_valor + "%'";
                        }
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException ex) {
            }
        }
        if (campos == null) {
            campos = "";
        }
        System.out.println("from " + tabela + campos);
        inicia_sessao();

        try {
            Query query = sessao.createQuery("from " + tabela + campos);
            lista = query.list();
            sessao.flush();
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

}
