package com.andersen.lesson10.services;

import com.andersen.lesson9.Models.Ticket;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;

@Repository
public interface TicketDao extends CrudRepository<Ticket, Integer> {
    Ticket getById(int i);

    ArrayList<Ticket> findAll(int userId);
}
