package com.andersen.lesson10;

import com.andersen.lesson10.services.TicketDaoImpl;
import com.andersen.lesson10.services.UserDaoImpl;
import com.andersen.lesson9.Models.Ticket;
import com.andersen.lesson9.Models.TicketType;
import com.andersen.lesson9.Models.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;

import java.util.logging.Logger;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = {"com.andersen.lesson10.services.*, com.andersen.lesson9.Models.*"})
@EnableJpaRepositories(basePackages = "com.andersen.lesson10.services.*")
@EntityScan("com.andersen.lesson9.Models.*")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, LiquibaseAutoConfiguration.class, JooqAutoConfiguration.class, R2dbcAutoConfiguration.class})
@Import({ HibernateConf.class})
@EnableTransactionManagement
public class MappingApplicationSpring implements CommandLineRunner {
    @Autowired
    private static TicketDaoImpl ticketDao;
    @Autowired
    private static UserDaoImpl userDao;
    private static final Logger logger = Logger.getLogger(String.valueOf(MappingApplicationSpring.class));

    @Override
    public void run(String... args) throws Exception {
    }


    public static void main(String[] args) {
        SpringApplication.run(MappingApplicationSpring.class, args);

        ApplicationContext ctx = new AnnotationConfigApplicationContext(HibernateConf.class);
        Environment env = ctx.getEnvironment();

        userDao = (UserDaoImpl) ctx.getBean("userDao");
        ticketDao = (TicketDaoImpl) ctx.getBean("ticketDao");
        daoCode();
    }

    @PostConstruct
    private static void daoCode(){

        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket5 = new Ticket();
        StringBuilder builder1 = new StringBuilder();

        User user1 = new User();
        user1.setName("Name1");

        try {
            //for user id=1 saving
            userDao.save(user1);

            //for ticket id=1 saving
            ticket5.setTicketType(TicketType.DAY);
            User userSet = userDao.getById(1);
            ticket5.setUser(userSet);
            try {
                ticketDao.save(ticket5);

                if(ticket5!=null) {
                    ticket5.setTicketType(TicketType.WEEK);
                    ticketDao.save(ticket5);
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
                userDao.save(user2);
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
                ticketDao.save(ticket3);
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
            userDao.save(user5);

            //for ticket saving with user_id=2
            ticket1.setTicketType(TicketType.MONTH);
            User userSet3 = userDao.getById(2);
            ticket1.setUser(userSet3);
            try {
                ticketDao.save(ticket1);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //for ticket saving with user_id=2
            ticket2.setTicketType(TicketType.DAY);
            User userSet4 = userDao.getById(2);
            ticket2.setUser(userSet4);
            try {
                ticketDao.save(ticket2);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //get ticket with id=2
            Ticket ticket4 = ticketDao.getById(2);
            logger.info(builder1.append("Ticket with id 2 is: ").append(ticket4==null?" Not found.":ticket4.toString()).toString());

            //get ticket list with user_id=2
            ArrayList<Ticket> list = new ArrayList<Ticket>();

            list = (ArrayList<Ticket>) ticketDao.findAll(2);

            if(list!=null)
                for(Ticket t:list){
                    builder1 = new StringBuilder();
                    logger.info(builder1.append("In the list found ticket with id: ").append(t.getId()).toString());
                }

            if(user5!=null) {
                //delete user with id=2
                userDao.deleteById(2);

                ArrayList<Ticket> list2 = new ArrayList<Ticket>();
                //check if tickets with user_id=2 exists

                list2 = (ArrayList<Ticket>) ticketDao.findAll(2);

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
