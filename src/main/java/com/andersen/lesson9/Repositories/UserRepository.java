package com.andersen.lesson9.Repositories;

import com.andersen.lesson9.MappingApplication;
import com.andersen.lesson9.Models.Ticket;
import com.andersen.lesson9.Models.User;
import jakarta.persistence.*;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
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
        EntityManager em = sessionFactory.createEntityManager();

        newTransaction = em.getTransaction();

        try {
            if (user.getId() == null) {
                em.persist(user);
            } else {
                em.merge(user);
            }
        } catch (Exception ex) {
            logger.severe("Problem saving user entity <{}>"+user.getId());
            logger.severe(ex.getMessage());
            newTransaction.rollback();
        } finally {
            commitTransactionIfNeeded(newTransaction);
        }
        em.flush();
        logger.info("User is saved: {}"+user.getId());
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
        EntityManager em = sessionFactory.createEntityManager();
        TypedQuery<User> query = em.createQuery("From User Where id="+id.toString(), User.class);
        return query.getSingleResult();
    }

    public void deleteUserById(Integer id) {
        EntityManager em = sessionFactory.createEntityManager();
        Query query = em.createQuery("DELETE FROM Ticket WHERE user_id="+id.toString());
        query.executeUpdate();
        query = em.createQuery("DELETE FROM User WHERE id="+id.toString());
        query.executeUpdate();
    }

    public void updateTicketByUserIdAndUserId(User user, Integer id) {
        EntityManager em = sessionFactory.createEntityManager();
        Query query = em.createQuery("UPDATE User SET id="+id.toString()+" WHERE id="+user.getId().toString());
        query.executeUpdate();
        query = em.createQuery("UPDATE Ticket SET user_id="+id.toString()+" WHERE user_id="+user.getId().toString());
        query.executeUpdate();
    }

}
