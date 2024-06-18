package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;

import java.io.FileInputStream;
import java.io.IOException;
/**
 * Hello world!
 *
 */
public class App 
{
    private static Logger logger = Logger.getLogger(String.valueOf(App.class));

    public static void main(String[] args) throws JsonProcessingException {
        int x = 0;
        int countValidTicketTypes = 0;
        int countValidStartDate = 0;
        int countValidPrice = 0;
        int countInValidRules = 0;
        int countValidTickets = 0;
        StringBuilder builder = new StringBuilder();

        do {
            String input = getInput();
            BusTicket ticket= new ObjectMapper().readValue(input, BusTicket.class);
            logger.info(ticket.toString());

            if (Validator.validateTicketType(ticket)) countValidTicketTypes++;
            else countInValidRules++;

            if (Validator.validateStartDate(ticket)) countValidStartDate++;
            else countInValidRules++;

            if (Validator.validatePrice(ticket)) countValidPrice++;
            else countInValidRules++;

            if (Validator.validateTicketType(ticket) && Validator.validateStartDate(ticket) && Validator.validatePrice(ticket)) countValidTickets++;
            if(countInValidRules>1) logger.severe("More than one error!");

            countInValidRules = 0;
            x++;
        } while (x < 5);

        logger.info(builder.append("\nTotal = ").append(x).append("\nValid = ").append(countValidTickets)
                .append(mostPopularViolation(countValidTicketTypes, countValidStartDate, countValidPrice, x)).toString());

        //reading from file:
        builder = new StringBuilder();
        try {
            x = 0;
            countValidTicketTypes = 0;
            countValidStartDate = 0;
            countValidPrice = 0;
            countInValidRules = 0;
            countValidTickets = 0;

            ArrayList<String> s = stream(0);
            do {
                String input = s.get(x);
                BusTicket ticket= new ObjectMapper().readValue(input, BusTicket.class);
                logger.info(ticket.toString());

                if (Validator.validateTicketType(ticket)) countValidTicketTypes++;
                else countInValidRules++;

                if (Validator.validateStartDate(ticket)) countValidStartDate++;
                else countInValidRules++;

                if (Validator.validatePrice(ticket)) countValidPrice++;
                else countInValidRules++;

                if (Validator.validateTicketType(ticket) && Validator.validateStartDate(ticket) && Validator.validatePrice(ticket)) countValidTickets++;
                if(countInValidRules>1) logger.severe("More than one error!");

                countInValidRules = 0;
                x++;
            } while (s.size()>x);

            logger.info(builder.append("\nTotal = ").append(x).append("\nValid = ").append(countValidTickets)
                    .append(mostPopularViolation(countValidTicketTypes, countValidStartDate, countValidPrice, x)).toString());

        } catch (Exception ignored){
            System.out.println(ignored.getMessage());
        }
    }

    public static ArrayList<String> stream(int x) throws IOException {
        StringBuilder builderS = new StringBuilder();
        ArrayList<String> data = new ArrayList<String>();
        try(FileInputStream fin=new FileInputStream("C:\\Users\\Zhanat\\data.txt"))
        {
            //the content of file is corrected to have 82 chars at each line so the buffer is 84 bytes
            byte[] buffer = new byte[84];

            int count;
            while((count=fin.read(buffer))!=-1) {
                builderS = new StringBuilder();

                for (int i = 0; i < count; i++) {
                    char c = (char) buffer[i];
                    builderS.append(c);
                }
                buffer = new byte[84];
                x++;
                data.add(builderS.toString());
            }
        }
        catch(IOException ex){

            logger.severe(ex.getMessage());
        }
        return data;
    }

    private static String getInput() {
        return new Scanner(System.in).nextLine();
    }

    private static String mostPopularViolation(int validTicketTypes, int validStartDate, int validPrice, int totalTickets) {
        int invalidTicketTypes = totalTickets - validTicketTypes;
        int invalidStartDate = totalTickets - validStartDate;
        int invalidPrice = totalTickets - validPrice;
        int[] invalids = new int[]{invalidTicketTypes, invalidStartDate, invalidPrice};
        invalids = Arrays.stream(invalids).sorted().toArray();

        StringBuilder builderStr = new StringBuilder();
        builderStr.append("\nMost popular violation = ");

        if (invalids[2]==invalidTicketTypes) builderStr.append("\"ticket types\"");
        else if (invalids[2]==invalidStartDate) builderStr.append("\"start date\"");
        else builderStr.append("\"price\"");

        if (invalids[1]==invalidTicketTypes && invalids[2]!=invalids[1]) builderStr.append(" or \"ticket types\"");
        else if (invalids[1]==invalidStartDate && invalids[2]!=invalids[1]) builderStr.append(" or \"start date\"");
        else builderStr.append(" or \"price\"");

        return builderStr.toString();
    }
}
