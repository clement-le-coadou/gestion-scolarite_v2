package dao;



import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jpa.Cours;

import java.util.List;

public class CoursDAO {

    private SessionFactory sessionFactory;

    public CoursDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Cr�ation d'un nouveau cours avec `persist`
    public void createCours(Cours cours) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(cours); // Remplace `save` par `persist`
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Cours findCoursById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Cours.class, id);
        }
    }


    // Mise � jour d'un cours existant avec `merge`
    public void updateCours(Cours cours) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(cours); // Remplace `update` par `merge`
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteCours(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Cours cours = session.get(Cours.class, id);
            if (cours != null) {
                session.remove(cours); // Utilisation de `remove`
                System.out.println("Cours supprim� : " + cours.getNom());
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void close() {
        sessionFactory.close();
    }
}
