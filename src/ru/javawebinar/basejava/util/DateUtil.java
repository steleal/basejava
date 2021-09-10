package ru.javawebinar.basejava.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM/YYYY");

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        return date == null ? "" : date.equals(NOW) ? "сейчас" : DTF.format(date);
    }

    public static LocalDate parse(String date) {
        return StringUtil.isNullOrEmpty(date) ? null
                : date.equalsIgnoreCase("сейчас") ? NOW
                : parse0(date);
    }

    private static LocalDate parse0(String date) {
        try {
            String [] values = date.split("/");
            return LocalDate.of(
                    Integer.parseInt(values[1]),
                    Integer.parseInt(values[0]),
                    1);
        } catch (Exception e) {
            return null;
        }
    }
}
