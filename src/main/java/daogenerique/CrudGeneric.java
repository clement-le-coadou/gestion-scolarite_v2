package daogenerique;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jpa.Cours;
import jpa.Etudiant;

import org.hibernate.SessionFactory;
import java.util.List;

public class CrudGeneric<T> implements GenericDAO<T> {

    private final Class<T> entityType;
    private final SessionFactory sessionFactory;

    public CrudGeneric(SessionFactory sessionFactory, Class<T> entityType) {
        this.sessionFactory = sessionFactory;
        this.entityType = entityType;
    }

    @Override
    public void create(T entity) {
        Transaction transaction = null;
        Session session = null; // Declare session here
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            System.out.println("SUCCESS !!!!!!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback transaction in case of error
            }
            e.printStackTrace();  // Log the exception stack trace
        } finally {
            if (session != null) {
                session.close(); // Close the session here
            }
        }
    }

    @Override
    public T read(Long id) {
        Session session = null; // Declare session here
        try {
            session = sessionFactory.openSession();
            return session.find(entityType, id);
        } catch (Exception e) {
            e.printStackTrace();  // Log any exceptions that occur
            return null;  // Return null if an error occurs
        } finally {
            if (session != null) {
                session.close(); // Close the session here
            }
        }
    }
    
    public T findByEmail(String email) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            String hql = "from " + entityType.getName() + " where email = :email";
            return session.createQuery(hql, entityType)
                          .setParameter("email", email)
                          .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace(); // Log de l'exception
            return null;
        } finally {
            if (session != null) {
                session.close(); // Fermeture de la session
            }
        }
    }


    @Override
    public void update(T entity) {
        Transaction transaction = null;
        Session session = null; // Declare session here
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback transaction in case of error
            }
            e.printStackTrace();  // Log the exception stack trace
        } finally {
            if (session != null) {
                session.close(); // Close the session here
            }
        }
    }

    @Override
    public void delete(T entity) {
        Transaction transaction = null;
        Session session = null; // Declare session here
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback transaction in case of error
            }
            e.printStackTrace();  // Log the exception stack trace
        } finally {
            if (session != null) {
                session.close(); // Close the session here
            }
        }
    }

    @Override
    public List<T> findAll() {
        Session session = null; // Declare session here
        try {
            session = sessionFactory.openSession();
            return session.createQuery("from " + entityType.getName(), entityType).list();
        } catch (Exception e) {
            e.printStackTrace();  // Log any exceptions that occur
            return null;  // Return null if an error occurs
        } finally {
            if (session != null) {
                session.close(); // Close the session here
            }
        }
    }

    public T findById(int id) {
        Session session = null;
        T entity = null;
        try {
            session = sessionFactory.openSession(); // Ouvrir une nouvelle session
            entity = session.get(entityType, id);
        } finally {
            if (session != null) {
                session.close(); // Fermer la session après utilisation
            }
        }
        return entity;
    }


}
