package com.andersen.lesson10;

import com.andersen.lesson10.controllers.TicketController;
import com.andersen.lesson10.services.NotificationSenderImpl;
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

import java.util.Set;
import java.util.logging.Logger;
import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = {"com.andersen.lesson10.controllers.*, com.andersen.lesson10.services.*, com.andersen.lesson9.Models.*"})
@EnableJpaRepositories(basePackages = "com.andersen.lesson10.services.*")
@EntityScan("com.andersen.lesson9.Models.*")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, LiquibaseAutoConfiguration.class, JooqAutoConfiguration.class, R2dbcAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@Import({ HibernateConf.class, MvcConfig.class, WebSecurityConfig.class})
@EnableTransactionManagement
public class MappingApplicationSpring implements CommandLineRunner {
    @Autowired
    private static TicketDaoImpl ticketDao;
    @Autowired
    private static UserDaoImpl userDao;
    @Autowired
    private static TicketController ticketController;

    private static final Logger logger = Logger.getLogger(String.valueOf(MappingApplicationSpring.class));

    private static Resource dataFile;

    @Autowired
    private static NotificationSenderImpl notify;

    @Override
    public void run(String... args) throws Exception {
    }


    public static void main(String[] args) {
        SpringApplication.run(MappingApplicationSpring.class, args);

        ApplicationContext ctx = new AnnotationConfigApplicationContext(HibernateConf.class);
        Environment env = ctx.getEnvironment();
        logger.info(env.getProperty("notification.service"));

        userDao = (UserDaoImpl) ctx.getBean("userDao");
        ticketDao = (TicketDaoImpl) ctx.getBean("ticketDao");
        ticketController = (TicketController) ctx.getBean("ticketController");

        daoCode();

        dataFile = ctx.getResource("classpath:data.txt");
        ArrayList<String> list = stream();

        notify = (NotificationSenderImpl) ctx.getBean("ThisIsMyFirstConditionalBean");
        logger.info(notify.send("Message"));

    }

    @PostConstruct
    private static ArrayList<String> stream(){
        StringBuilder builderS = new StringBuilder();
        ArrayList<String> data = new ArrayList<String>();
        try
        {
            byte[] dataContentByte = dataFile.getContentAsByteArray();
            byte[][] dataEachRow = new byte[17][84];
            int j = 0, row = 0, lastByte = 84;
            for(int i=0; i<dataContentByte.length; i++,j++){
                if(j==lastByte){
                    data.add(builderS.toString());
                    logger.info(builderS.toString());
                    j = 0;
                    row++;
                    if(row==16) lastByte=82;
                    builderS = new StringBuilder();
                }
                dataEachRow[row][j] = dataContentByte[i];

                char c = (char) dataContentByte[i];
                builderS.append(c);
            }
            data.add(builderS.toString());
            logger.info(builderS.toString());

            //the content of file is corrected to have 82 chars at each line so the buffer is 84 bytes
        }
        catch(Exception ex){
            logger.severe(ex.getMessage());
        }
        return data;
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

            //for user id=1 duplicate key error not throwing, merging
            User user2 = new User();
            user2.setId(1);
            user2.setName("Name2");
            try {
                userDao.save(user2);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //for ticket id=1 duplicate key error not throwing, merging
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

            // change user name to NameUpdate and create a new Ticket)
            User userSetUpdate = userDao.getById(1);
            userSetUpdate.setName("NameUpdate");

            Ticket tNew = new Ticket();
            tNew.setUser(userSetUpdate);
            tNew.setTicketType(TicketType.MONTH);

            Set<Ticket> userSetUpdateTicketList = userSetUpdate.getTicketList();
            userSetUpdateTicketList.add(tNew);
            userSetUpdate.setTicketList(userSetUpdateTicketList);

            userDao.save(userSetUpdate);

        } catch (Exception e) {
            logger.info(e.getMessage());
        }

    }
}
