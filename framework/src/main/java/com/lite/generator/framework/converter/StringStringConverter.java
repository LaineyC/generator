package com.lite.generator.framework.converter;

import javafx.util.StringConverter;

public class StringStringConverter extends StringConverter<String> {

    public StringStringConverter(){

    }

    @Override
    public String toString(String object) {
        return object;
    }

    @Override
    public String fromString(String string) {
       return string;
    }

}
