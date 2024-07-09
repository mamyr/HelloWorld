package com.andersen.lesson10.services;

import com.andersen.lesson9.Models.Ticket;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Service
public class TicketDao {

    @Autowired
    private SessionFactory sessionFactory;

    public TicketDao(){}

    TicketDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public void setTemplate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional
    public void saveTicket(Ticket e){
        sessionFactory.openSession().save(e);
    }

    @Transactional
    public void updateTicket(Ticket e){
        sessionFactory.openSession().update(e);
    }

    @Transactional
    public void deleteTicket(Ticket e){
        sessionFactory.openSession().delete(e);
    }
    public Ticket getById(int id){
        Ticket e=(Ticket)sessionFactory.openSession().get(Ticket.class,id);
        return e;
    }
    //method to return all tickets
    public List<Ticket> getTicketsByUserId(Integer id){
        Session session = sessionFactory.openSession();

        TypedQuery<Ticket> query = (TypedQuery<Ticket>) session.createQuery("From Ticket Where user.id="+id.toString());
        return query.getResultList();
    }

    public void initializeBean() {
        System.out.println("TicketDao Bean is initialized!!");
    }

    public void destroyBean() {
        System.out.println("TicketDao Bean is destroyed!!");
    }
}
