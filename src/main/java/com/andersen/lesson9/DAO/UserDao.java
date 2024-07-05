package com.andersen.lesson9.DAO;
import com.andersen.lesson9.Models.User;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class UserDao {
    HibernateTemplate template;
    public void setTemplate(HibernateTemplate template) {
        this.template = template;
    }
    public void saveUser(User e){
        template.save(e);
    }
    public void updateUser(User e){
        template.update(e);
    }
    public void deleteUser(User e){
        template.delete(e);
    }
    public User getById(int id){
        User e=(User)template.get(User.class,id);
        return e;
    }
}