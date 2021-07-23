package ru.javawebinar.basejava.util;

import java.time.LocalDate;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

}
