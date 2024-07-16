package com.andersen.lesson10.services;

import com.andersen.lesson9.Models.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketDao extends CrudRepository<Ticket, Integer> {
    Ticket getById(int i);

    List<Ticket> findAll(int userId);
}
