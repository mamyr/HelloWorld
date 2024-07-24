package com.andersen.lesson8;

import java.sql.Timestamp;

public class User {
    private int id = 0;
    private String name;
    private Timestamp creationDate;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setCreationDate(Timestamp creationDate){
        this.creationDate = creationDate;
    }
    public Timestamp getCreationDate(){
        return this.creationDate;
    }
}
