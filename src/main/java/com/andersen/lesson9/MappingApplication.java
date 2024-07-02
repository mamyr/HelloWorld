package com.andersen.lesson9;

import com.andersen.lesson9.Models.Ticket;
import com.andersen.lesson9.Models.TicketType;
import com.andersen.lesson9.Models.User;
import com.andersen.lesson9.Repositories.TicketRepository;
import com.andersen.lesson9.Repositories.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.persister.collection.mutation.RowMutationOperations;
import java.util.logging.Logger;
import java.util.ArrayList;

public class MappingApplication { //implements CommandLineRunner {
    private static TicketRepository ticketRepository;
    private static UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(String.valueOf(MappingApplication.class));

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
            //Create typesafe ServiceRegistry object
            StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
            sessionFactory = meta.getSessionFactoryBuilder().build();

            userRepository = new UserRepository(sessionFactory);
            ticketRepository = new TicketRepository(sessionFactory);

        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket5 = new Ticket();
        StringBuilder builder1 = new StringBuilder();

        User user1 = new User();
        user1.setName("Name1");
        try {
            //for user id=1 saving
            user1 = userRepository.save(user1);

            //for ticket id=1 saving
            ticket5.setTicketType(TicketType.DAY);
            User userSet = userRepository.getUserById(1);
            ticket5.setUser(userSet);
            try {
                ticket5 = ticketRepository.save(ticket5);

                if(ticket5!=null) {
                    ticket5.setTicketType(TicketType.WEEK);
                    ticketRepository.updateTicketType(ticket5);
                }
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //get user with id=1
            User user3 = userRepository.getUserById(1);
            builder1 = new StringBuilder();
            logger.info(builder1.append("User with id 1 is: ").append(user3==null?" Not found.":user3.toString()).toString());

            //for user id=1 duplicate key error throwing
            User user2 = new User();
            user2.setId(1);
            user2.setName("Name2");
            try {
                user2 = userRepository.save(user2);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //for ticket id=1 duplicate key error throwing
            Ticket ticket3 = new Ticket();
            ticket3.setId(1);
            ticket3.setTicketType(TicketType.WEEK);
            User userSet2 = userRepository.getUserById(2);
            ticket3.setUser(userSet2);
            try {
                ticket3 = ticketRepository.save(ticket3);
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
            user5 = userRepository.save(user5);

            //for ticket saving with user_id=2
            ticket1.setTicketType(TicketType.MONTH);
            User userSet3 = userRepository.getUserById(2);
            ticket1.setUser(userSet3);
            try {
                ticket1 = ticketRepository.save(ticket1);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //for ticket saving with user_id=2
            ticket2.setTicketType(TicketType.DAY);
            User userSet4 = userRepository.getUserById(2);
            ticket2.setUser(userSet4);
            try {
                ticket2 = ticketRepository.save(ticket2);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //get ticket with id=2
            Ticket ticket4 = ticketRepository.getTicketById(2);
            logger.info(builder1.append("Ticket with id 2 is: ").append(ticket4==null?" Not found.":ticket4.toString()).toString());

            //get ticket list with user_id=2
            ArrayList<Ticket> list = new ArrayList<Ticket>();
            list = (ArrayList<Ticket>) ticketRepository.getTicketByUserId(2);
            if(list!=null)
                for(Ticket t:list){
                    builder1 = new StringBuilder();
                    logger.info(builder1.append("In the list found ticket with id: ").append(t.getId()).toString());
                }

            if(user5!=null) {
                //delete user with id=2
                userRepository.deleteUserById(user5.getId());
                ArrayList<Ticket> list2 = new ArrayList<Ticket>();
                //check if tickets with user_id=2 exists
                list2 = (ArrayList<Ticket>) ticketRepository.getTicketByUserId(2);
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

    /* Method to CREATE a user in the database */
    public static Integer addUser(String name)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Integer userID = null;

        try {
            tx = session.beginTransaction();
            User user
                    = new User();
            user.setName(name);
            userID = (Integer)session.save(user);
            tx.commit();
        }
        catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            logger.info(e.getMessage());
        }
        finally {
            session.close();
        }
        return userID;
    }

    /* Method to CREATE a ticket in the database */
    public Integer addTicket(Integer userId, TicketType type)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Integer ticketID = null;

        try {
            tx = session.beginTransaction();
            Ticket ticket
                    = new Ticket();
            ticket.setTicketType(type);
            ticket.setId(userId);
            ticketID = (Integer)session.save(ticket);
            tx.commit();
        }
        catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            logger.info(e.getMessage());
        }
        finally {
            session.close();
        }
        return ticketID;
    }

    /* Method to CREATE a ticket in the database */
    public void deleteUserByIdAndTicketByUserId(User user)
    {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(session.load(user.getClass(), user.getId()));;
            tx.commit();
        }
        catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            logger.info(e.getMessage());
        }
        finally {
            session.close();
        }
    }
    /*    public static void main(String[] args) {
        SpringApplication.run(MappingApplication.class, args);
    }

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
            user1 = userRepository.save(user1);

            //for ticket id=1 saving
            ticket5.setTicketType(Ticket.TicketType.DAY);
            User userSet = userRepository.getUserById(1L);
            ticket5.setUser(userSet);
            try {
                ticket5 = ticketRepository.save(ticket5);

                if(ticket5!=null) {
                    ticket5.setTicketType(Ticket.TicketType.WEEK);
                    ticketRepository.updateTicketType(ticket5);
                }
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //get user with id=1
            User user3 = userRepository.getUserById(1L);
            builder1 = new StringBuilder();
            logger.info(builder1.append("User with id 1 is: ").append(user3==null?" Not found.":user3.toString()).toString());

            //for user id=1 duplicate key error throwing
            User user2 = new User();
            user2.setId(1L);
            user2.setName("Name2");
            try {
                user2 = userRepository.save(user2);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //for ticket id=1 duplicate key error throwing
            Ticket ticket3 = new Ticket();
            ticket3.setId(1L);
            ticket3.setTicketType(Ticket.TicketType.WEEK);
            User userSet2 = userRepository.getUserById(2L);
            ticket3.setUser(userSet2);
            try {
                ticket3 = ticketRepository.save(ticket3);
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
            user5 = userRepository.save(user5);

            //for ticket saving with user_id=2
            ticket1.setTicketType(Ticket.TicketType.MONTH);
            User userSet3 = userRepository.getUserById(2L);
            ticket1.setUser(userSet3);
            try {
                ticket1 = ticketRepository.save(ticket1);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //for ticket saving with user_id=2
            ticket2.setTicketType(Ticket.TicketType.DAY);
            User userSet4 = userRepository.getUserById(2L);
            ticket2.setUser(userSet4);
            try {
                ticket2 = ticketRepository.save(ticket2);
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            //get ticket with id=2
            Ticket ticket4 = ticketRepository.getTicketById(2L);
            logger.info(builder1.append("Ticket with id 2 is: ").append(ticket4==null?" Not found.":ticket4.toString()).toString());

            //get ticket list with user_id=2
            ArrayList<Ticket> list = new ArrayList<Ticket>();
            list = (ArrayList<Ticket>) ticketRepository.getTicketByUserId(2L);
            if(list!=null)
                for(Ticket t:list){
                    builder1 = new StringBuilder();
                    logger.info(builder1.append("In the list found ticket with id: ").append(t.getId()).toString());
                }

            if(user5!=null) {
                //delete user with id=2
                userRepository.deleteUserById(user5.getId());
                ArrayList<Ticket> list2 = new ArrayList<Ticket>();
                //check if tickets with user_id=2 exists
                list2 = (ArrayList<Ticket>) ticketRepository.getTicketByUserId(2L);
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
    }*/
}
