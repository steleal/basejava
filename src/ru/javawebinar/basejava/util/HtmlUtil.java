package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.ContactType;

public class HtmlUtil {

    public static String toHtml(ContactType type, String value) {
        if (type == null || value == null) return "";

        String title = type.getTitle();
        switch (type) {
            case PHONE:
            case MOBILE:
            case HOME_PHONE:
                return title + ": " + value;
            case SKYPE:
                return title + ": " + toLink("skype:" + value, value);
            case MAIL:
                return title + ": " + toLink("mailto:" + value, value);
            case LINKEDIN:
            case GITHUB:
            case STACKOVERFLOW:
            case HOME_PAGE:
                return toLink(value, title);
            default:
                return "";
        }
    }

    public static String toLink(String url, String title) {
        return "<a href='" + url + "'>" + title + "</a>";
    }

}
