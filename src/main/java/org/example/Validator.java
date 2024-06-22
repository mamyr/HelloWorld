package org.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

public class Validator {
    private static Logger logger = Logger.getLogger(String.valueOf(Validator.class));
    private static ArrayList<String> validTicketTypes;

    Validator(){
        validTicketTypes = new ArrayList<String>();
        validTicketTypes.add("DAY");
        validTicketTypes.add("WEEK");
        //new validator MONTH added
        validTicketTypes.add("MONTH");
        validTicketTypes.add("YEAR");
    };

    public static boolean validateTicketType(BusTicket ticket){
        new Validator();
        return validTicketTypes.contains(ticket.getTicketType());
    }

    public static boolean validateStartDate(BusTicket ticket){
        Date date = new Date();
        Date ticketDate = new Date();

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        try {
            if(!(ticket.getStartDate()==null || ticket.getStartDate().isEmpty()))
                ticketDate = simpleDateFormat.parse(ticket.getStartDate());
            else ticketDate = new Date();

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return !(ticket.getStartDate()==null || ticket.getStartDate().isEmpty()) && ticketDate.before(date);
    }

    public static boolean validatePrice(BusTicket ticket){
        return !(ticket.getPrice()==null || ticket.getPrice().isEmpty() || ticket.getPrice().equals("0")) && Integer.parseInt(ticket.getPrice()) % 2 == 0;
    }

}
