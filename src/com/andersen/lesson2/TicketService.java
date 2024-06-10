package com.andersen.lesson2;

import org.jetbrains.annotations.NotNull;

import java.util.Date;


public class TicketService implements Service {
    public static void main(String[] args) {
        Ticket empty_ticket = new Ticket();
        try {
            System.out.println("empty ticket id is:" + empty_ticket.getId());
        } catch (Exception ignored){
            System.out.println("Variable id is null in class AnyClass");
        }
        long time_now = new Date().getTime();
        Ticket full_ticket = new Ticket("A001","qwertyuiop",201,time_now,true,'B',405.45);
        Ticket limited_ticket = new Ticket("asdfghjklm", 301, time_now);

        save_create_time(time_now, full_ticket);
        save_price(4, limited_ticket);

        Client c = new Client();
        Ticket t = c.getTicket();
        Admin a = new Admin();
        a.checkTicket(t);
    }

    public static void save_create_time(long time, @org.jetbrains.annotations.NotNull Ticket t){
        t.setCreat_time(time/1000L);
        System.out.println("Creation time is saved.");
    }

    public static void save_price(int price, @NotNull Ticket t){
        t.setPrice(price);
        System.out.println("Price is saved.");
    }

    public void shareByPhone(String phone, Ticket ticket){
        System.out.println("Share ticket ID="+ticket.getID()+" by this phone:"+phone);
    }

    public void shareByPhoneAndEmail(String phone, String email, Ticket ticket){
        System.out.println("Share ticket ID="+ticket.getID()+" by this phone:"+phone+" and email:"+email);
    }

  }
