package com.andersen.lesson10.services;

import com.andersen.lesson9.Models.User;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {
    User getById(int i);
}