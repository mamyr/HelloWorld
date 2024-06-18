package com.andersen.lesson2;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ticket extends AnyClass {
    private static Logger logger = Logger.getLogger(String.valueOf(Ticket.class));

    private static final Pattern IDTemplate = Pattern.compile("\\w{4}");
    private static final Pattern concertHallTemplate = Pattern.compile("[a-zA-Z]{10}");
    private static final Pattern eventCodeTemplate = Pattern.compile("\\d{3}");
    private static final Pattern stadiumSectorTemplate = Pattern.compile("[ABC]");

    private String ID = "0000";
    private String concertHall = "concealer";
    private int eventCode = 100;
    private long time = System.currentTimeMillis() / 1000L;
    private boolean isPromo = false;
    private char stadiumSector = 'A';
    private double backpackWeight = 1000.12;

    private long creatTime = System.currentTimeMillis() / 1000L;
    private int price = 13;

    public Ticket() {
        this.ID = "0000";
        this.concertHall = "concealer";
        this.eventCode = 200;
        this.isPromo = false;
        this.stadiumSector = 'C';
        this.backpackWeight = 2000.12;
        //this.setId(1);
        this.setPrice(100);
        this.setCreatTime(System.currentTimeMillis() / 1000L);
    }

    public Ticket(String IDVar, String concertHallVar, int eventCodeVar, long timeVar, boolean isPromoVar, char stadiumSectorVar, double backpackWeightVar) {
        StringBuilder userError = new StringBuilder();
        Matcher m = IDTemplate.matcher(IDVar);
        boolean idInput = m.matches();

        if (!idInput) {
            userError.append("ID input must be of length 4 and consist of characters or integers.\n");
        } else {
            this.ID = IDVar;
        }

        m = concertHallTemplate.matcher(concertHallVar);
        boolean concertHallInput = m.matches();

        if (!concertHallInput) {
            userError.append("Concert hall input must be of length 10 and consist of characters.\n");
        } else {
            this.concertHall = concertHallVar;
        }

        m = eventCodeTemplate.matcher(String.valueOf(eventCodeVar));
        boolean eventCodeInput = m.matches();

        if (!eventCodeInput) {
            userError.append("Event code input must be of length 3 and consist of integers.\n");
        } else {
            this.eventCode = eventCodeVar;
        }

        this.time = timeVar / 1000L;
        this.isPromo = isPromoVar;

        m = stadiumSectorTemplate.matcher(String.valueOf(stadiumSectorVar));
        boolean stadiumSectorInput = m.matches();

        if (!stadiumSectorInput) {
            userError.append("Stadium sector input must be of length 1 and consist of characters: A, B or C.\n");
        } else {
            this.stadiumSector = stadiumSectorVar;
        }

        this.backpackWeight = backpackWeightVar;

        this.setId(1);
        this.setPrice(100);
        this.setCreatTime(System.currentTimeMillis() / 1000L);

        if (!userError.toString().isEmpty()) {
            logger.severe(userError.toString());
        } else {
            StringBuilder info = new StringBuilder();
            info.append("id=").append(this.getId()).append("ID: ").append(this.ID).append(", concert hall:").append(this.concertHall).append(", event code:").append(this.eventCode).append(", time:").append(this.time).append(", promo:").append(this.isPromo).append(", stadium sector:").append(this.stadiumSector).append(", backpack weight:").append(this.backpackWeight).append(", price=").append(this.price).append(", creat time=").append(this.creatTime);
            logger.info(info.toString());
        }
    }

    public Ticket(String concertHallVar, int eventCodeVar, long timeVar) {
        StringBuilder userError = new StringBuilder();
        Matcher m = concertHallTemplate.matcher(concertHallVar);
        boolean concertHallInput = m.matches();

        if (!concertHallInput) {
            userError.append("Concert hall input must be of length 10 and consist of characters.\n");
        } else {
            concertHall = concertHallVar;
        }

        m = eventCodeTemplate.matcher(String.valueOf(eventCodeVar));
        boolean eventCodeInput = m.matches();

        if (!eventCodeInput) {
            userError.append("Event code input must be of length 3 and consist of integers.\n");
        } else {
            eventCode = eventCodeVar;
        }

        time = timeVar / 1000L;
        if (!userError.toString().isEmpty()) {
            logger.severe(userError.toString());
        } else {
            StringBuilder info = new StringBuilder();
            info.append("Concert hall:").append(this.concertHall).append(", event code:").append(this.eventCode).append(", time:").append(this.time);
            logger.info(info.toString());
        }
    }

    public long getCreatTime() {
        return this.creatTime;
    }

    public void setCreatTime(long time) {
        this.creatTime = time;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void print() {
        StringBuilder info = new StringBuilder();
        info.append("id: ").append(this.getId()).append(", ID: ").append(this.ID).append(", concert hall:").append(this.concertHall).append(", event code:").append(this.eventCode).append(", time:").append(this.time).append(", promo:").append(this.isPromo).append(", stadium sector:").append(this.stadiumSector).append(", backpack weight:").append(this.backpackWeight).append(", price=").append(this.price).append(", creat time=").append(this.creatTime);
        logger.info(info.toString());
    }

    public void setTimeAndStadiumSector(long time, char stadiumSector) {
        this.time = time;
        Matcher m = stadiumSectorTemplate.matcher(String.valueOf(stadiumSector));
        boolean stadiumSectorInput = m.matches();

        if (!stadiumSectorInput) {
            logger.info("Stadium sector input must be of length 1 and consist of characters: A, B or C.\nPlease, change the value.");
        } else {
            this.stadiumSector = stadiumSector;
        }
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getConcertHall() {
        return this.concertHall;
    }

    public void setConcertHall(String concertHall) {
        this.concertHall = concertHall;
    }

    public int getEventCode() {
        return this.eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean getIsPromo() {
        return this.isPromo;
    }

    public void setIsPromo(boolean isPromo) {
        this.isPromo = isPromo;
    }

    public char getStadiumSector() {
        return this.stadiumSector;
    }

    public void setStadiumSector(char stadiumSector) {
        this.stadiumSector = stadiumSector;
    }

    public double getBackpackWeight() {
        return this.backpackWeight;
    }

    public void setBackpackWeight(double backpackWeight) {
        this.backpackWeight = backpackWeight;
    }

    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append("id: ").append(this.getId()).append(", ID: ").append(this.ID).append(", concert hall:").append(this.concertHall).append(", event code:").append(this.eventCode).append(", time:").append(this.time).append(", promo:").append(this.isPromo).append(", stadium sector:").append(this.stadiumSector).append(", backpack weight:").append(this.backpackWeight).append(", price=").append(this.price).append(", creat time=").append(this.creatTime);
        return info.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket that = (Ticket) o;

        return that.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        int resultId = this.getId() != 0 ? this.getId() : 0;
        int resultID = this.ID != null ? this.ID.hashCode() : 0;
        int resultConcertHall = this.concertHall != null ? this.concertHall.hashCode() : 0;
        int resultTime = this.time != 0 ? String.valueOf(this.time).hashCode() : 0;
        int resultIsPromo = this.isPromo ? 1 : 0;
        int resultStadiumSector = String.valueOf(this.stadiumSector).equals("") ? 0 : String.valueOf(this.stadiumSector).hashCode();
        int resultBackpackWeight = this.backpackWeight != 0 ? String.valueOf(this.backpackWeight).hashCode() : 0;
        int resultCreateTime = this.creatTime != 0 ? String.valueOf(this.creatTime).hashCode() : 0;
        int result = this.getId() + 4 * resultID + 4 * resultConcertHall + 4 * resultTime + resultIsPromo + 4 * resultStadiumSector + 4 * resultBackpackWeight + this.price + 4 * resultCreateTime + this.eventCode;
        return result;
    }
}