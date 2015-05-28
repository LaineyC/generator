package com.lite.generator.framework.el;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Function {

    //表达式库前缀
    String prefix();

}
