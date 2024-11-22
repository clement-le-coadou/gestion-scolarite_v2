package dao;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jpa.Enseignant;

import java.util.List;

public class EnseignantDAO {

    private SessionFactory sessionFactory;

    public EnseignantDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void createEnseignant(Enseignant enseignant) {
        Transaction transaction = null;
        Session session = null; // D�clarer la session ici
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(enseignant);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // R�trogradation en cas d'erreur
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Fermer la session ici
            }
        }
    }



    public Enseignant findEnseignantById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Enseignant.class, id);
        }
    }

    // Mise � jour d'un enseignant existant avec `merge`
    public void updateEnseignant(Enseignant enseignant) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(enseignant); // Remplace `update` par `merge`
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteEnseignant(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Enseignant enseignant = session.get(Enseignant.class, id);
            if (enseignant != null) {
                session.remove(enseignant); // Utilisation de `remove`
                System.out.println("Enseignant supprim� : " + enseignant.getNom());
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

