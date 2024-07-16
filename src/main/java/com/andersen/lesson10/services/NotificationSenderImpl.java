package com.andersen.lesson10.services;

import org.springframework.stereotype.Component;

@Component
public class NotificationSenderImpl implements NotificationSender {
    @Override
    public String send(String message) {
        return "Email Notification: " + message;
    }
}
