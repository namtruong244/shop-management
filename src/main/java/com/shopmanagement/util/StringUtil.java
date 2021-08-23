package com.shopmanagement.util;

import org.springframework.util.StringUtils;

import java.util.Random;

public class StringUtil {

    public static String normalizeMessage(String str){
        StringBuilder tmp = new StringBuilder(StringUtils.capitalize(str.toLowerCase()));
        int index = -1;
        while((index = tmp.indexOf(".", index + 1)) > 0 && index + 1 != tmp.length()){
            tmp.replace(index + 2, index + 3, String.valueOf(tmp.charAt(index+2)).toUpperCase());
        }
        return tmp.toString();
    }

    public static String createTranslatorCode(String... params){
        return String.join(".", params);
    }

     public static String generateRandomAlphanumeric(){
        return generateRandomAlphanumeric(20);
    }

    public static String generateRandomAlphanumeric(int targetLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
