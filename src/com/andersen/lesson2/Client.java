package com.andersen.lesson2;

import java.util.logging.Logger;

public class Client extends User{
    private static Logger logger = Logger.getLogger(String.valueOf(Client.class));

    @Override
    public void printRole() {
        super.printRole();
        logger.info("Role for User is Client.");
    }

    public Ticket getTicket(){
        StringBuilder builder = new StringBuilder("");
        Ticket t = new Ticket();
        logger.info(builder.append("Client's ticket ID is:").append(t.getID()).toString());
        return t;
    }
}
