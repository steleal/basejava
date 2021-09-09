package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.ListSection;
import ru.javawebinar.basejava.model.Organization;
import ru.javawebinar.basejava.model.OrganizationSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.TextSection;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.storage = Config.get().getSqlStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        // Совмещаем здесь Create и Update
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        if (uuid == null
                || uuid.trim().isEmpty()
                || fullName == null
                || fullName.trim().isEmpty()) {
            response.sendRedirect("resume");
            return;
        }
        uuid = uuid.trim();
        fullName = fullName.trim();

        boolean isNew = "true".equals(request.getParameter("isNew"));
        Resume r = isNew ? new Resume("") : storage.get(uuid);

        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            Section section = mapToSection(type, request);
            if (section != null) {
                r.addSection(type, section);
            } else {
                r.getSections().remove(type);
            }
        }
        if (isNew) {
            storage.save(r);
        } else {
            storage.update(r);
        }

        response.sendRedirect("resume");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }

        Resume r;
        switch (action) {
            case "delete":
                if (uuid != null) storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = storage.get(uuid);
                request.setAttribute("resume", r);
                request.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
                return;
            case "add":
                r = new Resume("");
                replaceNullSectionsWithEmpty(r);
                request.setAttribute("resume", r);
                request.setAttribute("isNew", true);
                request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
                return;
            case "edit":
                r = storage.get(uuid);
                replaceNullSectionsWithEmpty(r);
                request.setAttribute("resume", r);
                request.setAttribute("isNew", false);
                request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

    }

    private Section mapToSection(SectionType type, HttpServletRequest request) {
        String value = request.getParameter(type.name());
        if (value == null || value.isEmpty()) return null;

        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                // TextSection
                value = value.trim();
                return value.isEmpty() ? null : new TextSection(value);
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                // ListSection
                List<String> items = Arrays.stream(value.split("\\r?\\n"))
                        .filter(r -> !r.isEmpty())
                        .collect(Collectors.toList());
                return items.size() == 0 ? null : new ListSection(items);
            case EXPERIENCE:
            case EDUCATION:
                // OrganizationSection
                return null;
            default:
                throw new UnsupportedOperationException("Unsupported section type " + type.name());
        }
    }

    private void replaceNullSectionsWithEmpty(Resume r) {
        for (SectionType type : SectionType.values()) {
            Section section = r.getSection(type);
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    if (section == null) {
                        section = new TextSection();
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    if (section == null) {
                        section = new ListSection();
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    if (section == null) {
                        section = new OrganizationSection();
                    }
                    List<Organization> organizations = ((OrganizationSection) section).getOrganizations();
                    organizations.add(new Organization());
                    for (Organization org : organizations) {
                        List<Organization.Position> positions = org.getPositions();
                        positions.add(new Organization.Position());
                    }
                    break;
            }
            r.addSection(type, section);
        }
    }

}
