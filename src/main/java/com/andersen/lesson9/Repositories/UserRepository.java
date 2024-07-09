package com.andersen.lesson9.Repositories;

import com.andersen.lesson9.Models.User;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.logging.Logger;

public class UserRepository {
    private final java.util.logging.Logger logger = Logger.getLogger(String.valueOf(UserRepository.class));

    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    private EntityManagerFactory entityManagerFactory;

    public UserRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public User save(User user) {
        EntityTransaction newTransaction = null;
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        /*EntityManager em = sessionFactory.createEntityManager();

        newTransaction = em.getTransaction();*/

        try {
            if (user.getId() == null) {
                session.persist(user);  //em.persist(user);
            } else {
                session.merge(user);
            }
        } catch (Exception ex) {
            logger.severe("Problem saving user entity <{}>"+user.getId());
            logger.severe(ex.getMessage());
            t.rollback();//newTransaction.rollback();
        } finally {
            t.commit();//commitTransactionIfNeeded(newTransaction);
        }
        //em.flush();
        logger.info("User is saved: {}"+user.getId());
        session.close();
        return user;
    }

    private void commitTransactionIfNeeded(EntityTransaction newTransaction) {
        if (newTransaction != null && newTransaction.isActive()) {
            if (!newTransaction.getRollbackOnly()) {
                newTransaction.commit();
            }
        }
    }

    public User getUserById(Integer id){
        //EntityManager em = sessionFactory.createEntityManager();
        Session session = sessionFactory.openSession();
        TypedQuery<User> query = (TypedQuery<User>) session.createQuery("From User Where id="+id.toString());
        return query.getSingleResult();
    }

    public void deleteUserById(Integer id) {
        //EntityManager em = sessionFactory.createEntityManager();
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        Query query = (Query) session.createQuery("DELETE FROM Ticket WHERE user.id="+id.toString());
        query.executeUpdate();
        query = (Query) session.createQuery("DELETE FROM User WHERE id="+id.toString());
        query.executeUpdate();
        t.commit();
        session.close();
    }

    public void updateTicketByUserIdAndUserId(User user, Integer id) {
        //EntityManager em = sessionFactory.createEntityManager();
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        Query query = (Query) session.createQuery("UPDATE User SET id="+id.toString()+" WHERE id="+user.getId().toString());
        query.executeUpdate();
        query = (Query) session.createQuery("UPDATE Ticket SET user_id="+id.toString()+" WHERE user_id="+user.getId().toString());
        query.executeUpdate();
        t.commit();
        session.close();
    }

}
