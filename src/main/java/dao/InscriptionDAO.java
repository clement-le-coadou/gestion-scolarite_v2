package dao;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jpa.Inscription;

import java.util.List;

public class InscriptionDAO {

    private SessionFactory sessionFactory;

    public InscriptionDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Création d'une nouvelle inscription avec `persist`
    public void createInscription(Inscription inscription) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(inscription); // Remplace `save` par `persist`
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Inscription findInscriptionById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Inscription.class, id);
        }
    }

    public List<Inscription> findAllInscriptions() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Inscription", Inscription.class).list();
        }
    }

    // Mise à jour d'une inscription existante avec `merge`
    public void updateInscription(Inscription inscription) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(inscription); // Remplace `update` par `merge`
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteInscription(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Inscription inscription = session.get(Inscription.class, id);
            if (inscription != null) {
                session.remove(inscription); // Utilisation de `remove`
                System.out.println("Inscription supprimée pour l'étudiant ID : " + inscription.getEtudiant().getId());
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

