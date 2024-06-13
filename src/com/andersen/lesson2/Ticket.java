package com.andersen.lesson2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ticket {
    private static final Pattern ID_template = Pattern.compile("\\w{4}");
    private static final Pattern concert_hall_template = Pattern.compile("[a-zA-Z]{10}");
    private static final Pattern event_code_template = Pattern.compile("\\d{3}");
    private static final Pattern stadium_sector_template = Pattern.compile("[ABC]");

    private String ID = "0000";
    private String concert_hall = "concealer";
    private int event_code = 100;
    private long time = System.currentTimeMillis() / 1000L;
    private boolean is_promo = false;
    private char stadium_sector = 'A';
    private double backpack_weight = 1000.12;

    private long creat_time = System.currentTimeMillis() / 1000L;
    private int price = 13;

    public Ticket(){
        this.ID = "0000";
        this.concert_hall = "concealer";
        this.event_code = 100;
        this.is_promo = false;
        this.stadium_sector = 'A';
        this.backpack_weight = 1000.12;
    }

    public Ticket(String ID_var, String concert_hall_var, int event_code_var, long time_var, boolean is_promo_var, char stadium_sector_var, double backpack_weight_var) {
        String user_error = "";
        Matcher m = ID_template.matcher(ID_var);
        boolean id_input = m.matches();

        if (!id_input) {
           user_error += "ID input must be of length 4 and consist of characters or integers.\n";
        } else {
            this.ID = ID_var;
        }

        m = concert_hall_template.matcher(concert_hall_var);
        boolean concert_hall_input = m.matches();

        if (!concert_hall_input) {
            user_error += "Concert hall input must be of length 10 and consist of characters.\n";
        } else {
            this.concert_hall = concert_hall_var;
        }

        m = event_code_template.matcher(String.valueOf(event_code_var));
        boolean event_code_input = m.matches();

        if (!event_code_input) {
            user_error += "Event code input must be of length 3 and consist of integers.\n";
        } else {
            this.event_code = event_code_var;
        }

        this.time = time_var/1000L;
        this.is_promo = is_promo_var;

        m = stadium_sector_template.matcher(String.valueOf(stadium_sector_var));
        boolean stadium_sector_input = m.matches();

        if (!stadium_sector_input) {
            user_error += "Stadium sector input must be of length 1 and consist of characters: A, B or C.\n";
        } else {
            this.stadium_sector = stadium_sector_var;
        }

        this.backpack_weight = backpack_weight_var;

        if (!user_error.isEmpty()) {
            System.out.println(user_error);
        } else {
            System.out.println("ID: "+this.ID+", concert hall:"+this.concert_hall+", event code:"+this.event_code+", time:"+this.time+", promo:"+this.is_promo+", stadium sector:"+this.stadium_sector+", backpack weight:"+this.backpack_weight);
        }
    }

    public Ticket(String concert_hall_var, int event_code_var, long time_var){
        String user_error = "";
        Matcher m = concert_hall_template.matcher(concert_hall_var);
        boolean concert_hall_input = m.matches();

        if (!concert_hall_input) {
            user_error += "Concert hall input must be of length 10 and consist of characters.\n";
        } else {
            concert_hall = concert_hall_var;
        }

        m = event_code_template.matcher(String.valueOf(event_code_var));
        boolean event_code_input = m.matches();

        if (!event_code_input) {
            user_error += "Event code input must be of length 3 and consist of integers.\n";
        } else {
            event_code = event_code_var;
        }

        time = time_var/1000L;
        if (!user_error.isEmpty()) {
            System.out.println(user_error);
        } else {
            System.out.println("Concert hall:"+this.concert_hall+", event code:"+this.event_code+", time:"+this.time);
        }
    }

    public long getCreat_time(){
        return this.creat_time;
    }

    public void setCreat_time(long time){
        this.creat_time = time;
    }

    public int getPrice(){
        return  this.price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public char getStadiumSector() {
        return this.stadium_sector;
    }
  
    public void setID(String id){
        this.ID = id;
    }

    public String getID(){

        return this.ID;
    }
}
