package io.lele.supermarket.pricer.application.utils;

public class StringHelper {

    public static boolean isEmpty(String text){
        return text == null || text.trim().isEmpty();
    }

    public static boolean isNotEmpty(String text){
        return !isEmpty(text);
    }
}
