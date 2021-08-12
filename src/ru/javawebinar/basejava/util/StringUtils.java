package ru.javawebinar.basejava.util;

public class StringUtils {

    public static String substringBetween(String string, String left, String right) {
        int start = string.indexOf(left);
        int end = string.indexOf(right);
        if (start < 0 || end < start) {
            return "";
        }
        start += left.length();
        return string.substring(start, end);
    }
}
