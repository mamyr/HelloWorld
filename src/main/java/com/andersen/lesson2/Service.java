package com.andersen.lesson2;

public interface Service {
    void shareByPhone(String phone, Ticket ticket);
    void  shareByPhoneAndEmail(String phone, String email, Ticket ticket);
}
