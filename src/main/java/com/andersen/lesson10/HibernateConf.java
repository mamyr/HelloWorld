package com.andersen.lesson10;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import com.andersen.lesson10.services.TicketDao;
import com.andersen.lesson10.services.UserDao;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConf {
    @PersistenceContext
    private EntityManagerFactory entityManagerFactory;


    @Bean(name = "dataSource")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/my_ticket_service_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return (DataSource) dataSource;
    }
    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
                new String[]{"com.andersen.lesson9.model", "com.andersen.lesson10.services"});
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }
    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                "hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty(
                "hibernate.show_sql", "true");
        hibernateProperties.setProperty(
                "hibernate.format_sql", "true");
        hibernateProperties.setProperty(
                "hibernate.current_session_context_class", "thread");
        hibernateProperties.setProperty(
                "hibernate.id.new_generator_mappings", "true");
        hibernateProperties.setProperty(
                "hibernate.connection.autocommit", "true");

        return hibernateProperties;
    }

    @Bean(name = "emf")
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setJpaVendorAdapter(jpaVendorAdapter());
        emf.setPackagesToScan("com.andersen.lesson9.Models", "com.andersen.lesson10.services");
        emf.setPersistenceUnitName("stadto");
        emf.afterPropertiesSet();
        return (EntityManagerFactory) emf.getObject();
    }

    @Bean(name = "jpaVendorAdapter")
    public HibernateJpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform("org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter");
        return jpaVendorAdapter;
    }

    @Bean(name = "d1")
    public UserDao userDao() {
        UserDao userDAO = new UserDao();
        userDAO.setTemplate(sessionFactory().getObject());

        return userDAO;
    }

    @Bean(name = "d2")
    public TicketDao ticketDao() {
        TicketDao ticketDAO = new TicketDao();
        ticketDAO.setTemplate(sessionFactory().getObject());

        return ticketDAO;
    }
}
