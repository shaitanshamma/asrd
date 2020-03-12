package com.kropotov.asrd.utils;

import java.time.LocalDate;

public class DateConversion {
    public static LocalDate dateFromStr(String str) {
        String[] tokens = str.split("[.]");
        if (tokens.length == 3) {
            return LocalDate.of(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[0]));
        } else {
            return LocalDate.of(9999, 9, 9);
        }
    }

    public static String strFromDate(LocalDate date) {
        System.out.println(date.toString());
        return date.toString();
    }
}
