package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private final Storage storage = Config.get().getSqlStorage();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // the same as .setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        response.getWriter().write(mapToTable(storage.getAllSorted()));
    }

    private String mapToTable(List<Resume> resumes) {
        StringBuilder builder = new StringBuilder();
        builder.append("<table>\n<tr><th>Uuid</th><th>Full Name</th></tr>\n");
        for (Resume resume : resumes) {
            builder.append("<tr><td>")
                    .append(resume.getUuid())
                    .append("</td><td>")
                    .append(resume.getFullName())
                    .append("</tr>\n");
        }
        builder.append("</table>");
        return builder.toString();
    }
}
