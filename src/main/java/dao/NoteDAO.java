package dao;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jpa.Note;

import java.util.List;

public class NoteDAO {

    private SessionFactory sessionFactory;

    public NoteDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Cr�ation d'une nouvelle note avec `persist`
    public void createNote(Note note) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(note); // Remplace `save` par `persist`
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Note findNoteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Note.class, id);
        }
    }

    // Mise � jour d'une note existante avec `merge`
    public void updateNote(Note note) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(note); // Remplace `update` par `merge`
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteNote(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Note note = session.get(Note.class, id);
            if (note != null) {
                session.remove(note); // Utilisation de `remove`
                System.out.println("Note supprim�e pour l'�tudiant : " + note.getEtudiant().getPrenom() + " " + note.getEtudiant().getNom());
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

