package com.jasper.munselfservice.util;

import org.springframework.util.StringUtils;

import java.util.Locale;
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

    public static Integer parseIntBooleanOrDefault(String str, Integer defaultValue) {
        return parseBooleanOrDefault(str, defaultValue == 1) ? 1 : 0;
    }

    public static Boolean parseBooleanOrDefault(String str, Boolean defaultValue) {
        return parseOrDefault(str, v -> {
            v = v.strip().toLowerCase(Locale.ROOT);
            if (v.equals("true") || v.equals("1")) {
                return true;
            } else if (v.equals("false") || v.equals("0")) {
                return false;
            } else {
                throw new NumberFormatException();
            }
        }, defaultValue);
    }
}
