package com.andersen.lesson2;
import java.lang.Exception;

public abstract class AnyClass {

    @NullableWarning(value = "Variable id is null in class AnyClass")
    private Integer id;

    protected AnyClass() {
        //id = 0;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void print(){
        System.out.println("print content in console");
    }
}
