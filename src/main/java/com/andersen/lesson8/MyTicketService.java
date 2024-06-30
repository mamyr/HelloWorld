package com.andersen.lesson8;

import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MyTicketService {
    private static Logger logger = Logger.getLogger(String.valueOf(MyTicketService.class));

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        TicketDAO ticketDAO = new TicketDAO();
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket5 = new Ticket();
        StringBuilder builder1 = new StringBuilder();

        User user1 = new User();
        user1.setName("Name1");
        try {
            //for user id=1 saving
            user1 = userDAO.saveUser(user1);

            //for ticket id=1 saving
            ticket5.setTicketType(Ticket.TicketType.DAY);
            ticket5.setUserId(1);
            try {
                ticket5 = ticketDAO.saveTicket(ticket5);

                if(ticket5!=null) {
                    ticket5.setTicketType(Ticket.TicketType.WEEK);
                    boolean result = ticketDAO.updateTicketType(ticket5);
                }
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //get user with id=1
            User user3 = userDAO.getUserById(1);
            builder1 = new StringBuilder();
            logger.info(builder1.append("User with id 1 is: ").append(user3==null?" Not found.":user3.toString()).toString());

            //for user id=1 duplicate key error throwing
            User user2 = new User();
            user2.setId(1);
            user2.setName("Name2");
            try {
                user2 = userDAO.saveUser(user2);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //for ticket id=1 duplicate key error throwing
            Ticket ticket3 = new Ticket();
            ticket3.setId(1);
            ticket3.setTicketType(Ticket.TicketType.WEEK);
            ticket3.setUserId(2);
            try {
                ticket3 = ticketDAO.saveTicket(ticket3);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        User user5 = new User();
        user5.setName("Name5");
        try {
            //for user id=2 saving
            user5 = userDAO.saveUser(user5);

            //for ticket saving with user_id=2
            ticket1.setTicketType(Ticket.TicketType.MONTH);
            ticket1.setUserId(2);
            try {
                ticket1 = ticketDAO.saveTicket(ticket1);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //for ticket saving with user_id=2
            ticket2.setTicketType(Ticket.TicketType.DAY);
            ticket2.setUserId(2);
            try {
                ticket2 = ticketDAO.saveTicket(ticket2);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //get ticket with id=2
            Ticket ticket4 = ticketDAO.getTicketById(2);
            logger.info(builder1.append("Ticket with id 2 is: ").append(ticket4==null?" Not found.":ticket4.toString()).toString());

            //get ticket list with user_id=2
            ArrayList<Ticket> list = new ArrayList<Ticket>();
            list = ticketDAO.getTicketsByUserId(2);
            if(list!=null)
                for(Ticket t:list){
                    builder1 = new StringBuilder();
                    logger.info(builder1.append("In the list found ticket with id: ").append(t.getId()).toString());
                }

            if(user5!=null) {
                //delete user with id=2
                boolean result2 = userDAO.deleteUser(user5);
                ArrayList<Ticket> list2 = new ArrayList<Ticket>();
                //check if tickets with user_id=2 exists
                list2 = ticketDAO.getTicketsByUserId(2);
                builder1 = new StringBuilder();
                logger.info(builder1.append("Found tickets with userId 2 : ").append(list2==null?0:list2.size()).toString());

                if(list2!=null)
                    for (Ticket t : list2) {
                        builder1 = new StringBuilder();
                        logger.info(builder1.append("In the list found not deleted ticket with id: ").append(t.getId()).toString());
                    }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
