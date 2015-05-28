package com.lite.generator.framework.converter;

public class StringConverters {

    public final static StringBooleanConverter Boolean = new StringBooleanConverter(new String[]{"true", "false"});

    public final static StringNumberConverter<Integer> Integer = new StringNumberConverter<Integer>(){};

    public final static StringNumberConverter<Double> Double = new StringNumberConverter<Double>(){};

    public final static StringStringConverter String = new StringStringConverter();

    public final static StringDateConverter Date = new StringDateConverter();

    public final static StringEnumConverter Enum = new StringEnumConverter();

    public final static StringGroupConverter Group = new StringGroupConverter();

    public StringConverters(){

    }

}
