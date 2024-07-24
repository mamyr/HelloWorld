package com.andersen.lesson10.services;

import org.springframework.stereotype.Repository;

@Repository
public interface NotificationSender {
    String send(String message);
}
