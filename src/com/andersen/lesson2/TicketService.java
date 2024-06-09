package com.andersen.lesson2;

import java.util.Date;


public class TicketService {
    public static void main(String[] args) {
        Ticket empty_ticket = new Ticket();
        long time_now = new Date().getTime();
        Ticket full_ticket = new Ticket("A001","qwertyuiop",201,time_now,true,'B',405.45);
        Ticket limited_ticket = new Ticket("asdfghjklm", 301, time_now);

        Ticket[] tickets = new Ticket[10];
        for(int i=0; i<10; i++) {
            tickets[i] = new Ticket("000"+i,"qwertyuiop",201,time_now,true,'B',405.45);
        }
        printTickets(tickets);
        getTicketByID(tickets, "0000");
        
        empty_ticket.save_create_time(time_now);
        empty_ticket.save_price(4);
    }

    public static void printTickets(Ticket[] tickets){
        for(int i=0; i<tickets.length; i++){
            System.out.println("ticket.id["+i+"]="+tickets[i].getID());
        }
    }

    public static Ticket getTicketByID(Ticket[] tickets, String ID){
        int foundIndex = 0;
        for(int i=0; i<tickets.length; i++){
            if(tickets[i].getID().equals(ID)){
                System.out.println("ticket.id["+i+"]="+tickets[i].getID());
                foundIndex = i;
                break;
            }
        }
        return tickets[foundIndex];
    }
}
