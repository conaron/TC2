/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author Ton
 */
public class ConexaoDao {

    SessionFactory factory;
    Session sessao;

    public ConexaoDao() {
        try {
            factory = new AnnotationConfiguration().configure().buildSessionFactory();
            sessao = factory.openSession();
            sessao.beginTransaction();
        } catch (HibernateException e) {
            System.out.println("Criação da sessão falhou. " + e);
        }
    }

    public Session getSessao() {
        return sessao;
    }

    public void encerra_sessao() {
        sessao.close();
        factory.close();
    }

}
