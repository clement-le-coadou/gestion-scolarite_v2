package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jpa.Etudiant;

import java.util.List;

public class EtudiantDAO {

    private SessionFactory sessionFactory;

    public EtudiantDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Création d'un nouvel étudiant avec `persist`
    public void createEtudiant(Etudiant etudiant) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(etudiant); 
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Etudiant findEtudiantById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Etudiant.class, id);
        }
    }

    public List<Etudiant> findAllEtudiants() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Etudiant", Etudiant.class).list();
        }
    }

    // Mise à jour d'un étudiant existant avec `merge`
    public void updateEtudiant(Etudiant etudiant) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(etudiant); 
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteEtudiant(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Etudiant etudiant = session.get(Etudiant.class, id);
            if (etudiant != null) {
                session.remove(etudiant); 
                System.out.println("Étudiant supprimé : " + etudiant.getNom());
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

