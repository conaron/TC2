package util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

    SessionFactory factory;

    public HibernateUtil() {
        try {
            factory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (HibernateException ex) {
            System.out.println("Criação da sessão falhou. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public SessionFactory getFactory() {
        return factory;
    }

}
