package com.andersen.lesson10;

import com.andersen.lesson10.services.TicketDaoImpl;
import com.andersen.lesson10.services.UserDaoImpl;
import jakarta.persistence.EntityManagerFactory;

import jakarta.persistence.PersistenceUnit;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

@Configuration
public class HibernateConf {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;


    @Bean(name = "dataSource")
    @Scope("singleton")
    public static DriverManagerDataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/my_ticket_service_db");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    @Bean(name = "sessionFactory")
    public static SessionFactory sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
                new String[]{"com.andersen.lesson9.Models", "com.andersen.lesson10.services"});
        sessionFactory.setHibernateProperties(hibernateProperties());

        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        sessionFactory.setMetadataSources(new MetadataSources(ssr));
        Metadata meta = sessionFactory.getMetadataSources().getMetadataBuilder().build();

        return meta.getSessionFactoryBuilder().build();
    }
    private static final Properties hibernateProperties() {
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

    @Primary
    @Bean(name = "entityManagerFactory")
    public static EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setJpaVendorAdapter(jpaVendorAdapter());//(new HibernateJpaVendorAdapter());
        emf.setPackagesToScan("com.andersen.lesson9.Models", "com.andersen.lesson10.services");
        emf.setPersistenceUnitName("entityManagerFactoryUnit");
        //** Set the JPA provider explicitly (Hibernate)
        emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        emf.afterPropertiesSet();
        return (EntityManagerFactory) emf.getObject();
    }

    @Bean(name = "jpaVendorAdapter")
    public static HibernateJpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        return jpaVendorAdapter;
    }

    @Bean(name = "userDao")
    public static UserDaoImpl userDao() {
        UserDaoImpl userDAO = new UserDaoImpl();

        userDAO.setTemplate(sessionFactory());

        return userDAO;
    }

    @Bean(name = "ticketDao")
    public static TicketDaoImpl ticketDao() {
        TicketDaoImpl ticketDAO = new TicketDaoImpl();
        ticketDAO.setTemplate(sessionFactory());

        return ticketDAO;
    }
}
