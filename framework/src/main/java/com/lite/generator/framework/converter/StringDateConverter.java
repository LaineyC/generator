package com.lite.generator.framework.converter;

import javafx.beans.property.SimpleStringProperty;
import javafx.util.StringConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringDateConverter extends StringConverter<Date> {

    private SimpleDateFormat simpleDateFormat;

    private SimpleStringProperty pattern = new SimpleStringProperty();

    public StringDateConverter(){
        this("yyyy-MM-dd");
    }

    public StringDateConverter(String pattern){
        setPattern(pattern);
        this.simpleDateFormat = new SimpleDateFormat(pattern);
    }

    public String getPattern() {
        return pattern.get();
    }

    public SimpleStringProperty patternProperty() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern.set(pattern);
    }

    @Override
    public String toString(Date object) {
        if(object == null){
            return null;
        }
        return simpleDateFormat.format(object);
    }

    @Override
    public Date fromString(String string) {
        if(string == null){
            return null;
        }
        try{
            return simpleDateFormat.parse(string);
        }
        catch (Exception exception){
            return null;
        }
    }

}
