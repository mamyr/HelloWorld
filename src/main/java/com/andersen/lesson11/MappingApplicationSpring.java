package com.andersen.lesson10;

import com.andersen.lesson10.services.TicketDao;
import com.andersen.lesson10.services.UserDao;
import com.andersen.lesson9.Models.Ticket;
import com.andersen.lesson9.Models.TicketType;
import com.andersen.lesson9.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.util.logging.Logger;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@ComponentScan
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class MappingApplicationSpring implements CommandLineRunner {
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private UserDao userDao;
    private static final Logger logger = Logger.getLogger(String.valueOf(MappingApplicationSpring.class));

    @Override
    public void run(String... args) throws Exception {

        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket5 = new Ticket();
        StringBuilder builder1 = new StringBuilder();

        User user1 = new User();
        user1.setName("Name1");

        try {
            //for user id=1 saving
            userDao.saveUser(user1);

            //for ticket id=1 saving
            ticket5.setTicketType(TicketType.DAY);
            User userSet = userDao.getById(1);
            ticket5.setUser(userSet);
            try {
                ticketDao.saveTicket(ticket5);

                if(ticket5!=null) {
                    ticket5.setTicketType(TicketType.WEEK);
                    ticketDao.updateTicket(ticket5);
                }
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //get user with id=1
            User user3 = userDao.getById(1);
            builder1 = new StringBuilder();
            logger.info(builder1.append("User with id 1 is: ").append(user3==null?" Not found.":user3.toString()).toString());

            //for user id=1 duplicate key error throwing
            User user2 = new User();
            user2.setId(1);
            user2.setName("Name2");
            try {
                userDao.saveUser(user2);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //for ticket id=1 duplicate key error throwing
            Ticket ticket3 = new Ticket();
            ticket3.setId(1);
            ticket3.setTicketType(TicketType.WEEK);
            User userSet2 = userDao.getById(2);
            ticket3.setUser(userSet2);
            try {
                ticketDao.saveTicket(ticket3);
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
            userDao.saveUser(user5);

            //for ticket saving with user_id=2
            ticket1.setTicketType(TicketType.MONTH);
            User userSet3 = userDao.getById(2);
            ticket1.setUser(userSet3);
            try {
                ticketDao.saveTicket(ticket1);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //for ticket saving with user_id=2
            ticket2.setTicketType(TicketType.DAY);
            User userSet4 = userDao.getById(2);
            ticket2.setUser(userSet4);
            try {
                ticketDao.saveTicket(ticket2);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //get ticket with id=2
            Ticket ticket4 = ticketDao.getById(2);
            logger.info(builder1.append("Ticket with id 2 is: ").append(ticket4==null?" Not found.":ticket4.toString()).toString());

            //get ticket list with user_id=2
            ArrayList<Ticket> list = new ArrayList<Ticket>();
            list = (ArrayList<Ticket>) ticketDao.getTicketsByUserId(2);//user_id=2;
            if(list!=null)
                for(Ticket t:list){
                    builder1 = new StringBuilder();
                    logger.info(builder1.append("In the list found ticket with id: ").append(t.getId()).toString());
                }

            if(user5!=null) {
                //delete user with id=2
                userDao.deleteUser(user5);
                ArrayList<Ticket> list2 = new ArrayList<Ticket>();
                //check if tickets with user_id=2 exists
                list2 = (ArrayList<Ticket>) ticketDao.getTicketsByUserId(2);//user_id=2;
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


    public static void main(String[] args) {
        SpringApplication.run(MappingApplicationSpring.class, args);
    }
}
