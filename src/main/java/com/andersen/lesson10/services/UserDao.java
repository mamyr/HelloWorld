package com.andersen.lesson10.services;
import com.andersen.lesson9.Models.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    public UserDao(){}

    UserDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public void setTemplate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional
    public void saveUser(User e){
        sessionFactory.openSession().save(e);
    }

    @Transactional
    public void updateUser(User e){
        sessionFactory.openSession().update(e);
    }

    @Transactional
    public void deleteUser(User e){
        sessionFactory.openSession().delete(e);
    }

    public User getById(int id){
        User e=(User)sessionFactory.openSession().get(User.class,id);
        return e;
    }
    public void initializeBean() {
        System.out.println("UserDao Bean is initialized!!");
    }

    public void destroyBean() {
        System.out.println("UserDao Bean is destroyed!!");
    }
}