package com.lite.generator.framework.converter;

import javafx.util.StringConverter;

public class StringEnumConverter extends StringConverter<String> {

    public StringEnumConverter(){

    }

    @Override
    public String toString(String object) {
        return object;
    }

    @Override
    public String fromString(String string) {
        return string;
    }

    public String toString(String object, String... values) {
        if(!containsValue(object, values)){
            return null;
        }
        return object;
    }


    public String fromString(String string, String... values) {
        if(!containsValue(string, values)){
            return null;
        }
        return string;
    }

    private boolean containsValue(String value, String... values){
        if(value != null){
            for(String v : values){
                if(value == v || value.equals(v)){
                    return true;
                }
            }
        }
        return false;
    }

}
