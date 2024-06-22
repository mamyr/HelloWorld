package org.example;

public class BusTicket {
    private String ticketClass;

    private String ticketType;

    private String startDate;

    private String price;

    public String getTicketClass(){
        return this.ticketClass;
    }

    public void setTicketClass(String ticketClass){
        this.ticketClass = ticketClass;
    }

    public String getTicketType(){
        return this.ticketType;
    }

    public void setTicketType(String ticketType){
        this.ticketType = ticketType;
    }

    public String getStartDate(){
        return this.startDate;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public String getPrice(){
        return this.price;
    }

    public void setPrice(String price){
        this.price = price;
    }
}
