package com.andersen.lesson2;
import java.lang.Exception;
import java.util.logging.Logger;

public abstract class AnyClass {
    private static Logger logger = Logger.getLogger(String.valueOf(AnyClass.class));

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
        logger.info("print content in console");
    }
}
