package com.andersen.lesson2;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Logger;
import java.util.logging.Level;


public class TicketService implements Service {
    private static Logger logger = Logger.getLogger(String.valueOf(TicketService.class));

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder("");
        Ticket empty_ticket = new Ticket();
        try {
            logger.info(builder.append("empty ticket id is:").append(empty_ticket.getId()).toString());
        } catch (Exception ignored){
            logger.severe("Variable id is null in class AnyClass");
        }
        long time_now = new Date().getTime();
        Ticket full_ticket = new Ticket("A001","qwertyuiop",201,time_now,true,'B',405.45);
        Ticket limited_ticket = new Ticket("asdfghjklm", 301, time_now);

        saveCreateTime(time_now, full_ticket);
        savePrice(4, limited_ticket);

        Client c = new Client();
        Ticket t = c.getTicket();
        Admin a = new Admin();
        a.checkTicket(t);

        Ticket[] tickets = new Ticket[10];
        for(int i=0; i<10; i++) {
            tickets[i] = new Ticket("000"+i,"qwertyuiop",201,time_now,true,'B',405.45);
        }
        printTickets(tickets);
        getTicketByID(tickets, "0000");
        
        // creating a collection (ArrayList) with tickets
        final var ticketStorage = new ArrayList<Ticket>(3);
        ticketStorage.add(empty_ticket);
        ticketStorage.add(full_ticket);
        ticketStorage.add(limited_ticket);

        // trying to get some tickets by its stadiumSector value
        // and printing them for debug
        final var ticketWithStadiumSectorB = getTicketByStadiumSector(ticketStorage, 'B');
        System.out.printf(
                "Ticket from ticketStorage with stadiumSector == 'B': %s\n",
                ticketWithStadiumSectorB != null
                        ? ticketWithStadiumSectorB.getID()
                        : "doesn't exist!"
        );

        final var ticketWithStadiumSectorC = getTicketByStadiumSector(ticketStorage, 'C');
        System.out.printf(
                "Ticket from ticketStorage with stadiumSector == 'C': %s\n",
                ticketWithStadiumSectorC != null
                        ? ticketWithStadiumSectorC.getID()
                        : "doesn't exist!"
        );
    }

    public static void saveCreateTime(long time, @org.jetbrains.annotations.NotNull Ticket t){
        t.setCreat_time(time/1000L);
        logger.info("Creation time is saved.");
    }

    public static void savePrice(int price, @NotNull Ticket t){
        t.setPrice(price);
        logger.info("Price is saved.");
    }

    public void shareByPhone(String phone, Ticket ticket){
        StringBuilder builder = new StringBuilder("");
        logger.info(builder.append("Share ticket ID=").append(ticket.getID()).append(" by this phone:").append(phone).toString());
    }

    public void shareByPhoneAndEmail(String phone, String email, Ticket ticket){
        StringBuilder builder = new StringBuilder("");
        logger.info(builder.append("Share ticket ID=").append(ticket.getID()).append(" by this phone:").append(phone).append(" and email:").append(email).toString());
    }

    private static Ticket getTicketByStadiumSector(Collection<Ticket> tickets, char stadiumSector) {
        for (final var ticket : tickets) {
            if (ticket.getStadiumSector() == stadiumSector) {
                return ticket;
            }
        }
        return null;
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