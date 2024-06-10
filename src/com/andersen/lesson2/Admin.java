package com.andersen.lesson2;

public class Admin extends User{
    @Override
    public void printRole() {
        super.printRole();
        System.out.println("Role for User is Admin.");
    }

    public void checkTicket(Ticket t){
        System.out.println("Client's ticket ID is:"+t.getID()+" was checked");
    }
}
