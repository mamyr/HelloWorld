package com.andersen.lesson9.Models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@org.hibernate.annotations.NamedQueries({ @org.hibernate.annotations.NamedQuery(name = "Ticket_GetTicketById", query = "from Ticket where id = :id"),
        @org.hibernate.annotations.NamedQuery(name = "Ticket_GetTicketByUserId", query = "from Ticket where User.id = 2"),
        @org.hibernate.annotations.NamedQuery(name = "Ticket_UpdateTicketTypeById", query = "Update Ticket set ticketType = :newTicketType where id = :id")})
@Entity
@Table(name = "public.ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ticket_seq")
    @SequenceGenerator(name = "ticket_seq", sequenceName="ticket_seq")
    private Integer id;

    // A model belongs to one user
    // Foreign key referencing the user table
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name="ticket_type")
    private TicketType ticketType;
    private Timestamp creationDate;

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
