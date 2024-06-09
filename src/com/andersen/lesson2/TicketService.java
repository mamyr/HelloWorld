package com.andersen.lesson2;

import org.jetbrains.annotations.NotNull;

import java.util.Date;


public class TicketService {
    public static void main(String[] args) {
        Ticket empty_ticket = new Ticket();
        long time_now = new Date().getTime();
        Ticket full_ticket = new Ticket("A001","qwertyuiop",201,time_now,true,'B',405.45);
        Ticket limited_ticket = new Ticket("asdfghjklm", 301, time_now);

        save_create_time(time_now, full_ticket);
        save_price(4, limited_ticket);
    }

    public static void save_create_time(long time, @org.jetbrains.annotations.NotNull Ticket t){
        t.setCreat_time(time/1000L);
        System.out.println("Creation time is saved.");
    }

    public static void save_price(int price, @NotNull Ticket t){
        t.setPrice(price);
        System.out.println("Price is saved.");
    }

    public static Ticket getTicketByID(Ticket[] tickets, String ID){
        int found = 0;
        for(int i=0; i<tickets.length; i++){
            if(tickets[i].getID().equals(ID)){
                System.out.println("ticket.id["+i+"]="+tickets[i].getID());
                found = i;
                break;
            }
        }
        return tickets[found];
    }
}
