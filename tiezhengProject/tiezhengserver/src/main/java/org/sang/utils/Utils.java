package org.sang.utils;

public class Utils {
    public static boolean isBlank(Object o){
        String str = String.valueOf(o);
        return o == null || "".equals(str) && "null".equals(str);
    }
}
