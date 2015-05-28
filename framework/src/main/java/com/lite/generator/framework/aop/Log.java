package com.lite.generator.framework.aop;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Log {

    String content();

    Class<? extends ArgumentFormatter> argumentFormatter() default DefaultArgumentFormatter.class;

    Class<? extends ReturnValueFormatter> returnValueFormatter() default DefaultReturnValueFormatter.class;

}