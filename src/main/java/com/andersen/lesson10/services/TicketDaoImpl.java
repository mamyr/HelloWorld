package com.andersen.lesson10.services;

import com.andersen.lesson9.Models.Ticket;
import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class TicketDaoImpl implements TicketDao {
    private final Logger logger = Logger.getLogger(String.valueOf(TicketDaoImpl.class));

    private SessionFactory emf;

    public TicketDaoImpl(){
    }

    TicketDaoImpl(SessionFactory sessionFactory){
        this.emf = sessionFactory;
    }

    public void setTemplate(SessionFactory sessionFactory) {
        this.emf = sessionFactory;
    }

    @Override
    public Ticket getById(int i) {
        Session session = emf.openSession();

        TypedQuery<Ticket> query = (TypedQuery<Ticket>) session.createQuery("From Ticket Where id="+i);
        return query.getSingleResult();

    }

    @Override
    public ArrayList<Ticket> findAll(int userId) {
        Session session = emf.openSession();

        TypedQuery<Ticket> query = (TypedQuery<Ticket>) session.createQuery("From Ticket Where user.id="+userId);
        ArrayList<Ticket> list = new ArrayList<Ticket>();
        List queryList = query.getResultList();
        for(Object o:queryList){
            list.add((Ticket) o);
        }
        return list;
    }

    @Override
    public <S extends Ticket> S save(S ticket) {
        EntityTransaction newTransaction = null;
        Session session = emf.openSession();
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

    @Override
    public <S extends Ticket> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Ticket> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<Ticket> findAll() {
        return null;
    }

    @Override
    public Iterable<Ticket> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Ticket entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Ticket> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
