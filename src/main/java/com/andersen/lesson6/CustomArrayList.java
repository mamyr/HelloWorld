package com.andersen.lesson6;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class CustomArrayList<E> {
    private static Logger logger = Logger.getLogger(String.valueOf(CustomArrayList.class));
    private E[] array;
    private int size;

    public CustomArrayList(){
        this(10);
    }

    public CustomArrayList(int n){
        if (n > 0) {
            this.size = n;
            this.array = (E[]) new Object[n];
        } else if (n == 0) {
            this.array = (E[]) new Object[]{};
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + n);
        }
    }

    public void delete(int i){
        if (i>this.size) {
            throw new IllegalArgumentException("Illegal Capacity: " + i);
        } else if (i<0) {
            throw new IllegalArgumentException("Capacity can not be negative: " + i);
        }
        E[] newArray = (E[]) new Object[this.size-1];
        List<E> newList = Arrays.asList(newArray);
        int y=0, x=0;
        while(y<i){
            newList.set(x,this.array[y]);
            y++;
            x++;
        }
        y++;//y=i+1;
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
    }

    public E get(int i){
        if (i>this.size) {
            throw new IllegalArgumentException("Illegal Capacity: " + i);
        } else if (i<0) {
            throw new IllegalArgumentException("Capacity can not be negative: " + i);
        }
        return this.array[i];
    }

    public void put(int i, E element){
        if (i>this.size) {
            throw new IllegalArgumentException("Illegal Capacity: " + i);
        } else if (i<0) {
            throw new IllegalArgumentException("Capacity can not be negative: " + i);
        } else {
            this.array[i] = element;
        }
    }

    public void add(E element){
        int x=0;
        boolean notNull = false;
        while (x<this.size) {
            if (this.array[x] == null) {
                this.array[x] = element;
                break;
            } else notNull = true;
            x++;
        }

        if (this.size == x && notNull) {
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
        }

        logger.info(String.valueOf(Arrays.asList(this.array)));
    }

}
