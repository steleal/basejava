package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Link;
import ru.javawebinar.basejava.model.ListSection;
import ru.javawebinar.basejava.model.OrganizationSection;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.TextSection;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ru.javawebinar.basejava.util.DateUtil.NOW;

public class HtmlUtil {
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM/YYYY");

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

    public static String toHtml(SectionType type, Section section) {
        StringBuilder builder = new StringBuilder();
        builder.append(toTag("h2", type.getTitle()));
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                // TextSection
                builder.append(toTag("p", ((TextSection) section).getContent()));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                // ListSection
                builder.append("<ul>");
                ((ListSection) section).getItems().forEach(
                        item -> builder.append(toTag("li", item))
                );
                builder.append("</ul>");
                break;
            case EXPERIENCE:
            case EDUCATION:
                // OrganizationSection
                ((OrganizationSection) section).getOrganizations().forEach(org -> {
                    builder.append("<h3>").append(toHtml(org.getHomePage())).append("</h3>");
                    org.getPositions().forEach(pos -> {
                        LocalDate end = pos.getEndDate();
                        builder.append(
                                toTag("p",
                                        DTF.format(pos.getStartDate())
                                                + " - "
                                                + (end.equals(NOW) ? "сейчас" : DTF.format(end))))
                                .append(toTag("h4", pos.getTitle()))
                                .append(toTag("p", pos.getDescription()));
                    });
                });
                break;
            default:
                throw new UnsupportedOperationException("Unsupported section type " + type.name());
        }
        return builder.toString();
    }

    public static String toLink(String url, String title) {
        return "<a href='" + url + "'>" + title + "</a>";
    }

    public static String toHtml(Link link) {
        return toLink(link.getUrl(), link.getName());
    }

    public static String toTag(String tag, String value) {
        return "<" + tag + ">" + value + "</" + tag + ">";
    }
}
