package com.andersen.lesson2;

public class Client extends User{
    @Override
    public void printRole() {
        super.printRole();
        System.out.println("Role for User is Client.");
    }

    public Ticket getTicket(){
        Ticket t = new Ticket();
        System.out.println("Client's ticket ID is:"+t.getID());
        return t;
    }
}
