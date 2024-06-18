package com.andersen.lesson2;

import java.util.logging.Logger;

public abstract class User {
    private static Logger logger = Logger.getLogger(String.valueOf(User.class));

    public void printRole(){
        logger.info("Role is User.");
    }
}
