package com.andersen.lesson9.Repositories;

import com.andersen.lesson9.Models.Ticket;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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

/*        EntityManager em = sessionFactory.createEntityManager();
        newTransaction = em.getTransaction();*/
        try {
            if (ticket.getId() == null) {
                session.save(ticket); //em.persist(ticket);
            } /*else {
                em.merge(ticket);
            }*/
        } catch (Exception ex) {
            logger.severe("Problem saving individual entity <{}>"+ticket.getId());
            logger.severe(ex.getMessage());
            t.rollback();//newTransaction.rollback();
        } finally {
            t.commit();//commitTransactionIfNeeded(newTransaction);
        }
        //em.flush();
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
//        EntityManager em = sessionFactory.createEntityManager();
        Session session = sessionFactory.openSession();

        TypedQuery<Ticket> query = session.createQuery("From Ticket Where id="+id.toString(), Ticket.class);
        return query.getSingleResult();
    }

    public List<Ticket> getTicketByUserId(Integer id){
        //EntityManager em = sessionFactory.createEntityManager();
        Session session = sessionFactory.openSession();

        TypedQuery<Ticket> query = session.createQuery("From Ticket Where user.id="+id.toString(), Ticket.class);
        return query.getResultList();
    }

    @Transactional
    public void updateTicketType(Ticket ticket) {
        //EntityManager em = sessionFactory.createEntityManager();
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();

        logger.info("Updating ticket = "+ticket.getId());
        session.update(ticket);

        t.commit();
        session.close();
/*        Query query = em.createQuery("UPDATE Ticket SET ticket_type="+ticket.getTicketType().toString()+" WHERE id="+ticket.getId().toString());
        query.executeUpdate();*/
    }
}
