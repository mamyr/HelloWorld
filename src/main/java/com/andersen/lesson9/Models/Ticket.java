package com.andersen.lesson9.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.util.Objects;

@org.hibernate.annotations.NamedQueries({ @org.hibernate.annotations.NamedQuery(name = "Ticket_GetTicketById", query = "from Ticket where id = :id"),
        @org.hibernate.annotations.NamedQuery(name = "Ticket_GetTicketByUserId", query = "from Ticket t, User u where u.id = :userId and u=t.user"),
        @org.hibernate.annotations.NamedQuery(name = "Ticket_UpdateTicketTypeById", query = "Update Ticket set ticketType = :newTicketType where id = :id")})
@Entity
@Table(name = "public.ticket", uniqueConstraints = {@UniqueConstraint(columnNames={"id"})})
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ticket_seq")
    @SequenceGenerator(name = "ticket_seq", sequenceName="ticket_seq",
            initialValue = 1, allocationSize = 1)
    private Integer id;

    // A model belongs to one user
    // Foreign key referencing the user table
    @ManyToOne
    //@Cascade(value={CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(ignoreUnknown = true,
            value = {"hibernateLazyInitializer", "handler", "created"})
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name="ticket_type")
    private TicketType ticketType;
    @Column(name="creation_date")
    private Timestamp creationDate;

    public static Ticket from(User user, TicketType ticket_type) {
        Ticket ticket = new Ticket();
        ticket.setTicketType(ticket_type);
        ticket.setUser(user);
        return ticket;
    }

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setUser(User user){
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }

    public void setTicketType(TicketType ticketType){
        this.ticketType = ticketType;
    }
    public TicketType getTicketType(){
        return this.ticketType;
    }

    public void setCreationDate(Timestamp creationDate){
        this.creationDate = creationDate;
    }
    public Timestamp getCreationDate(){
        return this.creationDate;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Ticket user = (Ticket) o;
        return Objects.equals(id, user.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.id);
    }
}
