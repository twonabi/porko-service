package io.porko.utils;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class ConvertUtils {
    public static String convertToConstants(String value) {
        String regex = "([a-z])([A-Z])";
        String replacement = "$1_$2";

        return value.replaceAll(regex, replacement)
            .toUpperCase();
    }

    public static String YearMonthToString (YearMonth yearMonth) {
        return yearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public static YearMonth StringToYearMonth (String date) {
        return YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy-MM"));
    }
}
