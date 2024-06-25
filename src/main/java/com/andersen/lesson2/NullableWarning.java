package com.andersen.lesson2;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface NullableWarning {
    String value() default "";

    Class<? extends Exception> exception() default Exception.class;
}
