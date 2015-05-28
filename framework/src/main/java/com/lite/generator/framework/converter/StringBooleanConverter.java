package com.lite.generator.framework.converter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

public class StringBooleanConverter extends StringConverter<Boolean> {

    private ObservableList<String> format = FXCollections.observableArrayList();

    public StringBooleanConverter(String[] format){
        this.format.addAll(format);
    }

    public ObservableList<String> getFormat() {
        return format;
    }

    public void setFormat(ObservableList<String> format) {
        this.format = format;
    }

    @Override
    public String toString(Boolean object) {
        return object ? format.get(0) : format.get(1);
    }

    @Override
    public Boolean fromString(String string) {
        return format.get(0).equals(string);
    }

    public String toString(Boolean object, String... values) {
        return object ? values[0] : values[1];
    }


    public Boolean fromString(String string, String... values) {
        return values[0].equals(string);
    }

    public StringBooleanConverter newInstance(String[] format){
        return new StringBooleanConverter(format);
    }

}
