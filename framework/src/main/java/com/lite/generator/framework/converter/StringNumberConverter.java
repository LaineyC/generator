package com.lite.generator.framework.converter;

import com.lite.generator.framework.util.ReflectionUtil;
import javafx.util.StringConverter;

public class StringNumberConverter<T extends Number> extends StringConverter<T> {

    private Class<? extends Number> tClass = ReflectionUtil.getSuperClassGenericsType(this.getClass(), 0);

    public StringNumberConverter(){

    }

    @Override
    public String toString(T object) {
        if(object == null){
            return null;
        }
        return object.toString();
    }

    @Override
    public T fromString(String string) {
        try{
            return (T)tClass.getDeclaredConstructor(String.class).newInstance(string);
        }
        catch (Exception exception){
            return null;
        }
    }

}
