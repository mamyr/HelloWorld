package com.andersen.lesson9.Repositories;

import com.andersen.lesson9.Models.Ticket;
import jakarta.persistence.*;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.logging.Logger;

public class TicketRepository {
    private final Logger logger = Logger.getLogger(String.valueOf(TicketRepository.class));

    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    public TicketRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    private EntityManagerFactory entityManagerFactory;

    public Ticket save(Ticket ticket) {
        EntityTransaction newTransaction = null;
        EntityManager em = sessionFactory.createEntityManager();
        newTransaction = em.getTransaction();
        try {
            if (ticket.getId() == null) {
                em.persist(ticket);
            } else {
                em.merge(ticket);
            }
        } catch (Exception ex) {
            logger.severe("Problem saving individual entity <{}>"+ticket.getId());
            logger.severe(ex.getMessage());
            newTransaction.rollback();
        } finally {
            commitTransactionIfNeeded(newTransaction);
        }
        em.flush();
        logger.severe("Entity is saved: {}"+ticket.getId());
        return ticket;
    }

    private void commitTransactionIfNeeded(EntityTransaction newTransaction) {
        if (newTransaction != null && newTransaction.isActive()) {
            if (!newTransaction.getRollbackOnly()) {
                newTransaction.commit();
            }
        }
    }

    public Ticket getTicketById(Integer id){
        EntityManager em = sessionFactory.createEntityManager();
        TypedQuery<Ticket> query = em.createQuery("From Ticket Where id="+id.toString(), Ticket.class);
        return query.getSingleResult();
    }

    public List<Ticket> getTicketByUserId(Integer id){
        EntityManager em = sessionFactory.createEntityManager();
        TypedQuery<Ticket> query = em.createQuery("From Ticket Where user_id="+id.toString(), Ticket.class);
        return query.getResultList();
    }

    public void updateTicketType(Ticket ticket) {
        EntityManager em = sessionFactory.createEntityManager();
        Query query = em.createQuery("UPDATE Ticket SET ticket_type="+ticket.getTicketType().toString()+" WHERE id="+ticket.getId().toString());
        query.executeUpdate();
    }
}
