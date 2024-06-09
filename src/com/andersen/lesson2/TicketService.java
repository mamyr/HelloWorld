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
        empty_ticket.save_create_time(time_now);
        empty_ticket.save_price(4);
    }
}
