package com.andersen.lesson10.services;

import com.andersen.lesson9.Models.Ticket;
import com.andersen.lesson9.Models.User;
import jakarta.persistence.*;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class UserDaoImpl implements UserDao {
    private final Logger logger = Logger.getLogger(String.valueOf(UserDaoImpl.class));

    private SessionFactory emf;

    public UserDaoImpl(){
    }

    UserDaoImpl(SessionFactory sessionFactory){
        this.emf = sessionFactory;
    }

    public void setTemplate(SessionFactory sessionFactory) {
        this.emf = sessionFactory;
    }

    @Override
    public User getById(int i) {
        Session session = emf.openSession();
        TypedQuery<User> query = (TypedQuery<User>) session.createQuery("From User Where id="+i);
        return query.getSingleResult();

    }

    @Override
    public <S extends User> S save(S user) {
        EntityTransaction newTransaction = null;
        Session session = emf.openSession();
        Transaction t = session.beginTransaction();

        try {
            if (user.getId() == null) {
                session.persist(user);  //em.persist(user);
            } else {
                session.merge(user);
            }
        } catch (Exception ex) {
            logger.severe("Problem saving user entity <{}>"+user.getId());
            logger.severe(ex.getMessage());
            t.rollback();
        } finally {
            t.commit();
        }
        logger.info("User is saved: {}"+user.getId());
        session.close();
        return user;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Iterable<User> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {
        Session session = emf.openSession();
        Transaction t = session.beginTransaction();
        Query query = (Query) session.createQuery("DELETE FROM Ticket WHERE user.id="+integer.toString());
        query.executeUpdate();
        query = (Query) session.createQuery("DELETE FROM User WHERE id="+integer.toString());
        query.executeUpdate();
        t.commit();
        session.close();

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
