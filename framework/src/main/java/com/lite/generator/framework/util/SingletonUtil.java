package com.lite.generator.framework.util;

import java.util.HashMap;
import java.util.Map;

public class SingletonUtil {

    private static Map<Class<?>, Object> classMap = new HashMap<>();

    /*public static void put(Object object){
        classMap.put(object.getClass(), object);
    }*/

    public static <Clazz> Clazz get(Class<Clazz> clazz){
        if(!classMap.containsKey(clazz)){
            try {
                classMap.put(clazz, clazz.newInstance());
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return (Clazz)classMap.get(clazz);
    }

}
