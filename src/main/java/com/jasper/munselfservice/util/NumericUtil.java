package com.jasper.munselfservice.util;

import org.springframework.util.StringUtils;

import java.util.function.Function;

public class NumericUtil {
    public static<T> T parseOrDefault(String str, Function<String, T> parser, T defaultValue) {
        if (str == null) {
            return defaultValue;
        }

        str = str.trim().strip();

        if (!StringUtils.hasLength(str)) {
            return defaultValue;
        }

        try {
            return parser.apply(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Double parseDoubleOrDefault(String str, Double defaultValue) {
        return parseOrDefault(str, Double::parseDouble, defaultValue);
    }

    public static Integer parseIntOrDefault(String str, Integer defaultValue) {
        return parseOrDefault(str, Integer::parseInt, defaultValue);
    }
}
