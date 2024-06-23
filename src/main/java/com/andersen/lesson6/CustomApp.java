package com.andersen.lesson6;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.App;

import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Logger;

public class CustomApp {
    private static Logger logger = Logger.getLogger(String.valueOf(CustomApp.class));

    public static void main(String[] args) {
        CustomArrayList<String> array = new CustomArrayList<String>();
        array.add("0");
        array.put(0,"Zero");
        array.add("1");
        array.add("2");
        StringBuilder builder = new StringBuilder();
        logger.info(builder.append("element 0 is:").append(array.get(0)).toString());
        array.delete(2);

        CustomHashSet<String> set = new CustomHashSet<String>();
        set.add("0");
        set.put(0,"Zero");
        set.add("Zero");
        set.add("1");
        set.delete("Zero");
        StringBuilder builder1 = new StringBuilder();
        logger.info(builder1.append("HashSet contains \"1\": ").append(set.contains("1")).toString());
        Iterator<String> iterator = set.iterator();
        String next = iterator.next();
        while(iterator.hasNext() && next!=null){
            StringBuilder builder2 = new StringBuilder();
            logger.info(builder2.append("Element hash code is:").append(iterator.hashCode()).toString());
            next = iterator.next();
        }
    }
}
