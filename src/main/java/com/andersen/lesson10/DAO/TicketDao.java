package com.andersen.lesson9.DAO;

import com.andersen.lesson9.Models.Ticket;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.ArrayList;
import java.util.List;

public class TicketDao {
    HibernateTemplate template;
    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
    public void saveTicket(Ticket e){
        template.save(e);
    }
    public void updateTicket(Ticket e){
        template.update(e);
    }
    public void deleteTicket(Ticket e){
        template.delete(e);
    }
    public Ticket getById(int id){
        Ticket e=(Ticket)template.get(Ticket.class,id);
        return e;
    }
    //method to return all tickets
    public List<Ticket> getTicketsByUserId(){
        List<Ticket> list1=new ArrayList<Ticket>();
        List<Object> list=new ArrayList<Object>();
        list=  template.findByNamedQuery("Ticket_GetTicketByUserId",Ticket.class);
        for(Object o:list){
          list1.add((Ticket) o);
        };
        return list1;
    }

}
