package com.andersen.lesson6special;

import org.example.BusTicket;
import org.example.Validator;

import java.util.*;
import java.util.logging.Logger;

/**
 *Implementation of the service for the {@code BusTicket} class from the package {@link org.example.BusTicket}.
 *
 * <p>The {@code getTicketsByPriceRange}, {@code getTicketsByType}, {@code getTicketById}, {@code removeBusTicketById}
 * operations run in O(5) time.  The {@code createBusTicketById} operation runs in constant time.
 *
 * <p>For storage, it is used {@code ArrayList} instance with a <i>capacity</i> 5.
 *
 * <p><strong>Note that this implementation is not final.</strong> You can change the capacity of storage or
 * make another storage implementations.
 *
 * <p>If no object exists null is returned.</p>

 * <p>This class is a member of the
 * <a href="{@docRoot}/com/andersen/lesson6special/package-info.html">BusTicketService</a>.
 *
 * @author  Zhanat Kopbayeva
 * @see     BusTicket
 * @see     Validator
 * @since   1.0-SNAPSHOT
 */
public class BusTicketService {
    /**
     * The {@code Logger} is used for message logging by default on console.
     */
    private static Logger logger = Logger.getLogger(String.valueOf(BusTicketService.class));

    /**
     * The entry point for testing the operations for BusTicket. Following test operations are made:
     * <p>The {@code tickets} is initialised with initial capacity of 5 element storage.</p>
     * <p>The list {@code tickets} is filled with created tickets by incremented id, starting from 0.</p>
     * <p>The ticket with id 2 is removed from storage list {@code tickets}.</p>
     * <p>The {@code logger} outputs the ticket found by id 1</p>
     * <p>The search by ticket type is performed by {@code DAY}, {@code WEEK}, {@code MONTH}, {@code YEAR}.
     * The list is shown in output after search.</p>
     * <p>The search by ticket price is performed in the specified range from <i>5</i> to <i>10</i>.
     * The list is shown in output after search.</p>
     * @param args is not used.
     */
    public static void main(String[] args) {
        ArrayList<BusTicket> tickets = new ArrayList<BusTicket>(5);
        int x=0;
        do {
            tickets.add(createBusTicketById(x));
            x++;
        } while (x<5);

        removeBusTicketById(2, tickets);

        logger.info(getTicketById(1, tickets).toString());

        ArrayList<BusTicket> ticketsByDay = getTicketsByType("DAY", tickets);
        logger.info(String.valueOf(ticketsByDay));

        ArrayList<BusTicket> ticketsByWeek = getTicketsByType("WEEK", tickets);
        logger.info(String.valueOf(ticketsByWeek));

        ArrayList<BusTicket> ticketsByMonth = getTicketsByType("MONTH", tickets);
        logger.info(String.valueOf(ticketsByMonth));

        ArrayList<BusTicket> ticketsByYear = getTicketsByType("YEAR", tickets);
        logger.info(String.valueOf(ticketsByYear));

        ArrayList<BusTicket> ticketsByPrice = getTicketsByPriceRange(5, 10, tickets);
        logger.info(String.valueOf(ticketsByPrice));
    }

    /**
     * Method is used to get bus tickets in the specified price range, from price A to price B.
     * <p>Search is performed on the specified parameter input {@code storageList}</p>
     * <p>Returns the list of bus tickets if found in the {@code ArrayList}</p>, if not found empty list is returned.
     * @param fromPrice starting range of price
     * @param toPrice ending range of price
     * @param storageList list of stored tickets for making search on it
     * @return ArrayList
     */
    public static ArrayList<BusTicket> getTicketsByPriceRange(int fromPrice, int toPrice, ArrayList<BusTicket> storageList) {
        ArrayList<BusTicket> found = new ArrayList<BusTicket>();
        for (BusTicket element:storageList){
            if(Integer.valueOf(element.getPrice())>=fromPrice && Integer.valueOf(element.getPrice())<=toPrice) {
                found.add(element);
            }
        }
        return found;
    }

    /**
     * Method is used to get bus tickets by the specified ticket type.
     * <p>Ticket types that are valid are specified in the {@link org.example.Validator} class.</p>
     * <p>Search is performed on the specified parameter input {@code storageList}</p>
     * <p>Returns the list of bus tickets if found in the {@code ArrayList}</p>, if not found empty list is returned.
     * @param type valid type for bus ticket
     * @param storageList list of stored tickets for making search on it
     * @return ArrayList
     */
    public static ArrayList<BusTicket> getTicketsByType(String type, ArrayList<BusTicket> storageList) {
        ArrayList<BusTicket> found = new ArrayList<BusTicket>();
        for (BusTicket element:storageList){
            if(element.getTicketType().equals(type)) {
                found.add(element);
            }
        }
        return found;
    }

    /**
     * Method is used to get bus ticket by the specified ticket id.
     * <p>Search is performed on the specified parameter input {@code storageList}</p>
     * <p>Returns the bus ticket if found in the {@code BusTicket}</p>, if not found null is returned.
     * @param x ticket id
     * @param storageList list of stored tickets for making search on it
     * @return {@link org.example.BusTicket} class
     */
    public static BusTicket getTicketById(int x, ArrayList<BusTicket> storageList) {
        for (BusTicket element:storageList){
            if(element.getId()==x) {
                return element;
            }
        }
        return null;
    }

    /**
     * Method is used to remove bus ticket by the specified ticket id.
     * <p>Search is performed on the specified parameter input {@code storageList}</p>
     * <p>Removes the bus ticket if found in the {@code BusTicket}</p>, if not found, nothing is done.
     * @param x ticket id
     * @param storageList list of stored tickets for making search on it
     */
    public static void removeBusTicketById(int x, ArrayList<BusTicket> storageList) {
        for (BusTicket element:storageList){
            int index = storageList.indexOf(element);
            if(element.getId()==x) {
                storageList.remove(index);
                break;
            }
        }
    }

    /**
     * Method is used to create bus ticket by the specified ticket id.
     * <p>Input is performed until the specified parameter is valid.</p>
     * <p>Takes as input the start date and ticket type. Inputs must be valid as specified in {@link org.example.Validator} class.</p>
     * @param x ticket id
     * @return {@link org.example.BusTicket} class
     */
    public static BusTicket createBusTicketById(int x){
        BusTicket busTicket = new BusTicket();
        busTicket.setId(x);
        busTicket.setStartDate("");
        do {
            System.out.println("Specify the date of ticket in this format: \"yyyy-MM-dd\"");
            busTicket.setStartDate(new Scanner(System.in).nextLine());
        } while (!Validator.validateStartDate(busTicket));

        busTicket.setTicketType("");
        do {
            System.out.println("Specify the type of ticket from following: \"DAY\", \"WEEK\", \"MONTH\", \"YEAR\"");
            busTicket.setTicketType(new Scanner(System.in).nextLine());
        } while (!Validator.validateTicketType(busTicket));
        return busTicket;
    }
}
