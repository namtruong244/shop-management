package com.shopmanagement.util;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

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
        return Arrays.stream(params).collect(Collectors.joining("."));
    }
}
