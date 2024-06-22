package com.andersen.lesson2;

import java.util.logging.Logger;

public class Admin extends User{
    private static Logger logger = Logger.getLogger(String.valueOf(Admin.class));

    @Override
    public void printRole() {
        super.printRole();
        logger.info("Role for User is Admin.");
    }

    public void checkTicket(Ticket t){
        StringBuilder builder = new StringBuilder("");
        logger.info(builder.append("Client's ticket ID is:").append(t.getID()).append(" was checked").toString());
    }
}
