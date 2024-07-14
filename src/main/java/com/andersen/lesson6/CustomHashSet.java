package com.andersen.lesson6;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class CustomHashSet<E> {
    private static Logger logger = Logger.getLogger(String.valueOf(CustomHashSet.class));
    private E[] array;
    private int size;

    public CustomHashSet(){
        this(10);
    }

    public CustomHashSet(int n){
        if (n > 0) {
            this.size = n;
            this.array = (E[]) new Object[n];
        } else if (n == 0) {
            this.array = (E[]) new Object[]{};
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + n);
        }
    }

    public boolean contains(E element){
        int x=0;
        boolean found = false;
        while (x<this.size && !found) {
            found = this.array[x] == element;
            x++;
        }
        return found;
    }

    public Iterator<E> iterator(){
        List<E> list = Arrays.asList(this.array);
        return list.iterator();
    }

    private int getIndex(E element){
        int x=0;
        boolean found = false;
        while (x<this.size && !found) {
            found = this.array[x] == element;
            x++;
        }
        x = found?x-1:-1;
        return x;
    }

    public boolean delete(E element){
        int i = getIndex(element);
        if (i<0) {
            //throw new IllegalArgumentException("Element not found in this HashSet");
            return false;
        }

        E[] newArray = (E[]) new Object[this.size-1];
        List<E> newList = Arrays.asList(newArray);
        int y=0, x=0;
        while(y<i){
            newList.set(x,this.array[y]);
            y++;
            x++;
        }
        y++;
        while(y<=this.size-1){
            newList.set(x,this.array[y]);
            y++;
            x++;
        }
        @SuppressWarnings("unchecked")
        //the type cast is safe as the array has the type E[]
        E[] resultArray = (E[]) Array.newInstance(newArray.getClass().getComponentType(), 0);
        this.array = newList.toArray(resultArray);
        this.size -= 1;
        logger.info(String.valueOf(Arrays.asList(this.array)));
        return true;
    }


    public boolean put(int i, E element){
        if (i>this.size) {
            throw new IllegalArgumentException("Illegal Capacity: " + i);
        } else if (i<0) {
            throw new IllegalArgumentException("Capacity can not be negative: " + i);
        } else {
            boolean found = contains(element);
            if(!found){
                this.array[i] = element;
                logger.info(String.valueOf(Arrays.asList(this.array)));
                return true;
            } else return false;
        }
    }

    public boolean add(E element){
        boolean found = contains(element);
        int x=0;
        boolean notNull = false;
        while (x<this.size && !found) {
            if (this.array[x] == null) {
                this.array[x] = element;
                logger.info(String.valueOf(Arrays.asList(this.array)));
                return true;
            } else notNull = true;
            x++;
        }

        if (this.size == x && notNull && !found) {
            E[] newArray = (E[]) new Object[this.size+1];
            List<E> newList = Arrays.asList(newArray);
            int y=0;
            for (E item : this.array)
            {
                newList.set(y,item);
                y++;
            }

            newList.set(x, element);

            @SuppressWarnings("unchecked")
            //the type cast is safe as the array has the type E[]
            E[] resultArray = (E[]) Array.newInstance(this.array.getClass().getComponentType(), 0);
            this.array = newList.toArray(resultArray);
            this.size += 1;
            logger.info(String.valueOf(Arrays.asList(this.array)));
            return true;
        }
        logger.info("Element is already in set and was not added.");

        return false;
    }
}
