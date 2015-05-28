package com.lite.generator.framework.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

    public StringUtil() {

    }

    public static boolean isEmpty(String string){
        return StringUtils.isEmpty(string);
    }

    public static boolean isNotEmpty(String string){
        return StringUtils.isNotEmpty(string);
    }

    public static boolean isBlank(String string){
        return StringUtils.isBlank(string);
    }

    public static boolean isNotBlank(String string){
        return StringUtils.isNotBlank(string);
    }

    public static boolean hasWhitespace(String string){
        String trim = StringUtils.stripToNull(string);
        return trim == null || trim.length() != string.length();
    }

}
