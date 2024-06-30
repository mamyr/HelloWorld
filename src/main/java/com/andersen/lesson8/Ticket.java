package com.andersen.lesson8;

import java.sql.Timestamp;
import java.util.Date;

public class Ticket {

    public enum TicketType {
        DAY, WEEK, MONTH, YEAR
    }
    private int id = 0;
    private int userId;
    private TicketType ticketType;
    private Timestamp creationDate;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return this.userId;
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
}
