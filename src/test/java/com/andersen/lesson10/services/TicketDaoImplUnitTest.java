package com.andersen.lesson10.services;

import com.andersen.lesson10.dtos.TicketDTO;
import com.andersen.lesson9.Models.Ticket;
import com.andersen.lesson9.Models.TicketType;
import com.andersen.lesson10.dtos.UserDTO;
import com.andersen.lesson9.Models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TicketDaoImplUnitTest {
    private final User user1 = User.builder().id(1).name("Name1").creationDate(new Timestamp(123456789)).build();
    private final Ticket ticket1 = Ticket.builder().id(1).user(user1).ticketType(TicketType.MONTH).creationDate(new Timestamp(123456789)).build();
    private final Ticket ticket2 = Ticket.builder().id(2).user(user1).ticketType(TicketType.DAY).creationDate(new Timestamp(123456789)).build();

    private final Set<Ticket> ticketListDatabase = Set.of(this.ticket1, this.ticket2);

    @Mock
    private TicketDao ticketDao;

    @InjectMocks
    private TicketDaoImpl ticketDaoImpl;

    @BeforeAll
    public static void beforeAll() {
        MockitoAnnotations.openMocks(TicketDaoImplUnitTest.class);
    }

    @Test
    public void whenTicketSaved_thenControlFlowAsExpected() {
        Ticket ticketToSave = this.ticketListDatabase.stream().findFirst().get();
        Mockito.when(this.ticketDao.save(Mockito.any(Ticket.class)))
                .thenReturn(ticketToSave);

        this.ticketDaoImpl.save(ticketToSave);

        Mockito.verify(this.ticketDao, Mockito.times(1))
                .save(Mockito.any(Ticket.class));
    }

    @Test
    public void whenTicketSaved_thenControlFlowNotAsExpected() {
        Ticket ticketToSave = this.ticketListDatabase.stream().findFirst().get();
        Mockito.when(this.ticketDao.save(Mockito.any(Ticket.class)))
                .thenReturn(null); //may be duplicate restriction, nothing is saved

        this.ticketDaoImpl.save(ticketToSave);

        Mockito.verify(this.ticketDao, Mockito.times(1))
                .save(Mockito.any(Ticket.class));
    }

    @Test
    public void whenTicketSaved_thenControlFlowEdgeExpected() {
        Ticket ticketToSave = null;
        Mockito.when(this.ticketDao.save(Mockito.mock(Ticket.class)))
                .thenThrow(new SQLException("No data"));

        this.ticketDaoImpl.save(ticketToSave);//null can not be saved

        Mockito.verify(this.ticketDao, Mockito.times(1))
                .save(null);
    }

    @Test
    public void whenTicketGetById_thenControlFlowAsExpected() {
        Ticket ticketToGet = this.ticket1;
        Mockito.when(this.ticketDao.getById(1))
                .thenReturn(ticketToGet);

        Ticket ticketFound = this.ticketDaoImpl.getById(1);
        assertEquals(ticketToGet, ticketFound);

        Mockito.verify(this.ticketDao, Mockito.times(1))
                .getById(1);
    }

    @Test
    public void whenTicketGetById_thenControlFlowNotAsExpected() {
        Ticket ticketToGet = null;
        Mockito.when(this.ticketDao.getById(0))//id starts from 1
                .thenReturn(ticketToGet);//nothing is found

        Ticket ticketFound = this.ticketDaoImpl.getById(0);
        assertEquals(ticketToGet, ticketFound);

        Mockito.verify(this.ticketDao, Mockito.times(1))
                .getById(0);
    }

    @Test
    public void whenTicketGetById_thenControlFlowEdgeAsExpected() {
        Ticket ticketToGet = Mockito.mock(Ticket.class);//any ticket
        Mockito.when(this.ticketDao.getById(new Random().nextInt(2)))//id starts from 1
                .thenThrow(new SQLException("No connection"));

        Ticket ticketFound = this.ticketDaoImpl.getById(new Random().nextInt(2));
        assertEquals(null, ticketFound);

        Mockito.verify(this.ticketDao, Mockito.times(1))
                .getById(new Random().nextInt(2));
    }

    @Test
    public void whenTicketFindAll_thenControlFlowAsExpected() {//by user id imolements from CRUD repository, method name not changed
        List<Ticket> ticketsToGet = this.ticketListDatabase.stream().toList();
        Mockito.when(this.ticketDao.findAll(1)) //search for tickets with user id = 1
                .thenReturn(ticketsToGet);

        List<Ticket> ticketsFound = this.ticketDaoImpl.findAll(1);
        assertEquals(ticketsToGet, ticketsFound);

        Mockito.verify(this.ticketDao, Mockito.times(1))
                .findAll(1);
    }

    @Test
    public void whenTicketFindAll_thenControlFlowNotAsExpected() {//by user id imolements from CRUD repository, method name not changed
        List<Ticket> ticketsToGet = null;
        Mockito.when(this.ticketDao.findAll(0)) //search for tickets with user id = 0, id starts from 1
                .thenReturn(ticketsToGet);

        List<Ticket> ticketsFound = this.ticketDaoImpl.findAll(0);
        assertEquals(ticketsToGet, ticketsFound);

        Mockito.verify(this.ticketDao, Mockito.times(1))
                .findAll(0);
    }

    @Test
    public void whenTicketFindAll_thenControlFlowEdgeAsExpected() {//by user id imolements from CRUD repository, method name not changed
        List<Ticket> ticketsToGet = this.ticketListDatabase.stream().toList();
        Mockito.when(this.ticketDao.findAll(new Random().nextInt(1))) //search for tickets with user id = any, id starts from 1
                .thenThrow(new SQLException("No connection"));

        List<Ticket> ticketsFound = this.ticketDaoImpl.findAll(new Random().nextInt(1));
        assertEquals(null, ticketsFound);

        Mockito.verify(this.ticketDao, Mockito.times(1))
                .findAll(new Random().nextInt(1));
    }
}
