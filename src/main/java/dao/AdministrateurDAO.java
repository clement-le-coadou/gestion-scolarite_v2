package dao;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jpa.Administrateur;

import java.util.List;

public class AdministrateurDAO {

    private SessionFactory sessionFactory;

    public AdministrateurDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Création d'un nouvel administrateur avec `persist`
    public void createAdministrateur(Administrateur administrateur) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(administrateur);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Administrateur findAdministrateurById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Administrateur.class, id);
        }
    }

    public List<Administrateur> findAllAdministrateurs() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Administrateur", Administrateur.class).list();
        }
    }

    // Mise à jour d'un administrateur existant avec `merge`
    public void updateAdministrateur(Administrateur administrateur) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(administrateur); 
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteAdministrateur(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Administrateur administrateur = session.get(Administrateur.class, id);
            if (administrateur != null) {
                session.remove(administrateur);
                System.out.println("Administrateur supprimé : " + administrateur.getNom());
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
