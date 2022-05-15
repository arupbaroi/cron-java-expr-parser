package com.cronparser.domain.builder;

import java.util.regex.Pattern;

public class StringUtil {

    private static final Pattern INT_REGEX = Pattern.compile("\\d+");

    public static boolean isInt(String value) {
        return INT_REGEX.matcher(value).matches();
    }

}
