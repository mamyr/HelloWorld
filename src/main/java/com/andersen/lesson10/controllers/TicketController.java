package com.andersen.lesson10.controllers;

import com.andersen.lesson10.services.TicketDaoImpl;
import com.andersen.lesson9.Models.Ticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TicketController {
    private static final Logger logger = Logger.getLogger(String.valueOf(TicketController.class));

    @Autowired
    TicketDaoImpl ticketDao;

    public TicketController(TicketDaoImpl ticketDao){
        this.ticketDao = ticketDao;
    }

    public void setTicketDao(TicketDaoImpl ticketDao) {
        this.ticketDao = ticketDao;
    }

    @GetMapping("/tickets/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView getTicketById(@PathVariable int id){
        ModelAndView mav = new ModelAndView("ticket");
        Ticket t = ticketDao.getById(id);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            String ticketsAsString = objectMapper.writeValueAsString(ticketDao.getById(id));
            logger.info("t123456789 = "+t.toString());
            mav.addObject("ticket", ticketsAsString);
        } catch (Exception exception){
            logger.severe(exception.getMessage());
        }
        return mav;
    }

    @GetMapping("/tickets")
    public String all(Model model){
        model.addAttribute("tickets", ticketDao.findAll(1));
        logger.info("ticketDao123456789 = "+ticketDao.findAll(1).toString());
        return "tickets";
    }

    @RequestMapping(value = "/report/{username}/{password}", method = RequestMethod.GET)
    public @ResponseBody void generateReport(
            @PathVariable("username") String userName,
            @PathVariable("password") String password,
            HttpServletRequest request,
            HttpServletResponse response) {

        // ...
        // Here you can use the request and response objects like:
         response.setContentType("application/json");
         try {
             ObjectMapper objectMapper = new ObjectMapper();
             if(userName.equals("user") && password.equals("password")){
                 String ticketsAsString = objectMapper.writeValueAsString(ticketDao.getById(2));

                 response.getOutputStream().write(ticketsAsString.getBytes(StandardCharsets.UTF_8));
                 response.getOutputStream().close();
             } else {
                 String jsonString = "{\"error\":\"please provide username and password\"}";
                 response.getOutputStream().write(jsonString.getBytes(StandardCharsets.UTF_8));
                 response.getOutputStream().close();
             }
         } catch (Exception exception){
             logger.severe(exception.getMessage());
         }

    }
}
