package com.lite.generator.framework.util;

public class KeyGenerator {

    public KeyGenerator(){

    }

    private static long timeDivisor = System.currentTimeMillis();

    public static String timeHex(){
        long time = System.currentTimeMillis();
        timeDivisor = timeDivisor >= time ? timeDivisor + 1 : time;
        return Long.toHexString(timeDivisor);
    }

}
