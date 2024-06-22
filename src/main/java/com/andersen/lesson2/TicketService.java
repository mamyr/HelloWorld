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
        Ticket emptyTicket = new Ticket();
        try {
            logger.info(builder.append("empty ticket id is:").append(emptyTicket.getId()).toString());
        } catch (Exception ignored){
            logger.severe("Variable id is null in class AnyClass");
        }
        long timeNow = new Date().getTime();
        Ticket fullTicket = new Ticket("A001","qwertyuiop",201,timeNow,true,'B',405.45);
        Ticket limitedTicket = new Ticket("asdfghjklm", 301, timeNow);

        saveCreateTime(timeNow, fullTicket);
        savePrice(4, limitedTicket);

        Client c = new Client();
        Ticket t = c.getTicket();
        Admin a = new Admin();
        a.checkTicket(t);

        Ticket[] tickets = new Ticket[10];
        for(int i=0; i<10; i++) {
            tickets[i] = new Ticket("000"+i,"qwertyuiop",201,timeNow,true,'B',405.45);
        }
        printTickets(tickets);
        Ticket foundTicket = getTicketByID(tickets, "0000");
        
        // creating a collection (ArrayList) with tickets
        final var ticketStorage = new ArrayList<Ticket>(3);
        ticketStorage.add(emptyTicket);
        ticketStorage.add(fullTicket);
        ticketStorage.add(limitedTicket);

        // trying to get some tickets by its stadiumSector value
        // and printing them for debug
        final var ticketWithStadiumSectorB = getTicketByStadiumSector(ticketStorage, 'B');
        StringBuilder builder2 = new StringBuilder("");
        String infoMessage = ticketWithStadiumSectorB != null
                ? ticketWithStadiumSectorB.getID()
                : "doesn't exist!";

        logger.info(
                builder2.append("Ticket from ticketStorage with stadiumSector == 'B': ").append(infoMessage).append("\n").toString()
        );

        final var ticketWithStadiumSectorC = getTicketByStadiumSector(ticketStorage, 'C');
        StringBuilder builder3 = new StringBuilder("");
        String infoMessage2 = ticketWithStadiumSectorC != null
                ? ticketWithStadiumSectorC.getID()
                : "doesn't exist!";
        logger.info(
                builder3.append("Ticket from ticketStorage with stadiumSector == 'C': ").append(infoMessage2).append("\n").toString()
        );
    }

    public static void saveCreateTime(long time, @org.jetbrains.annotations.NotNull Ticket t){
        t.setCreatTime(time/1000L);
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
        StringBuilder builder = new StringBuilder("");
        for(int i=0; i<tickets.length; i++){
            builder.append("ticket.id[").append(i).append("]=").append(tickets[i].getID());
        }
        logger.info(builder.toString());
    }

  public static Ticket getTicketByID(Ticket[] tickets, String ID){
        int foundIndex = 0;
      StringBuilder builder = new StringBuilder("");
        for(int i=0; i<tickets.length; i++){
            if(tickets[i].getID().equals(ID)){
                builder.append("ticket.id[").append(i).append("]=").append(tickets[i].getID());
                foundIndex = i;
                break;
            }
        }
        logger.info(builder.toString());
        return tickets[foundIndex];
    }

}