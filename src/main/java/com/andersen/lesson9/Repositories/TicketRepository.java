package com.andersen.lesson9.Repositories;

import com.andersen.lesson9.Models.Ticket;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        try {
            if (ticket.getId() == null) {
                session.save(ticket);
            } else {
                session.merge(ticket);
            }
        } catch (Exception ex) {
            logger.severe("Problem saving individual entity <{}>"+ticket.getId());
            logger.severe(ex.getMessage());
            t.rollback();
        } finally {
            t.commit();
        }
        logger.info("Entity is saved: {}"+ticket.getId());
        session.close();
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
        Session session = sessionFactory.openSession();

        TypedQuery<Ticket> query = (TypedQuery<Ticket>) session.createQuery("From Ticket Where id="+id.toString());
        return query.getSingleResult();
    }

    public List<Ticket> getTicketByUserId(Integer id){
        Session session = sessionFactory.openSession();

        TypedQuery<Ticket> query = (TypedQuery<Ticket>) session.createQuery("From Ticket Where user.id="+id.toString());
        return query.getResultList();
    }

    public void updateTicketType(Ticket ticket) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        logger.info("Updating ticket = "+ticket.getId());
        session.update(ticket);

        t.commit();
        session.close();
    }
}
