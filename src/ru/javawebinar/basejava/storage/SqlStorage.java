package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.ListSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.TextSection;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        // Magic! If driver class load here then "No suitable driver found for jdbc" error fixed.
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No driver found for PostgreSQL database", e);
        }

        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.doInTransaction(conn -> {
            Resume resume = sqlHelper.executeInConn(conn, "SELECT uuid, full_name FROM resume WHERE uuid =?",
                    ps -> {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        if (!rs.next()) {
                            throw new NotExistStorageException(uuid);
                        }
                        return new Resume(uuid, rs.getString("full_name"));
                    });
            sqlHelper.executeInConn(conn, "SELECT resume_uuid, type, value FROM contact WHERE resume_uuid = ?",
                    ps -> {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            addContact(resume, rs);
                        }
                        return null;
                    });
            sqlHelper.executeInConn(conn, "SELECT resume_uuid, type, value FROM section WHERE resume_uuid = ?",
                    ps -> {
                        ps.setString(1, uuid);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            addSection(resume, rs);
                        }
                        return null;
                    });
            return resume;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.doInTransaction(conn -> {
            sqlHelper.executeInConn(conn, "UPDATE resume SET full_name = ? WHERE uuid = ?", (ps) -> {
                String uuid = r.getUuid();
                ps.setString(1, r.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) throw new NotExistStorageException(uuid);
                return null;
            });
            deleteContacts(r, conn);
            insertContacts(r, conn);
            deleteSections(r, conn);
            insertSections(r, conn);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.doInTransaction(conn -> {
            sqlHelper.executeInConn(conn, "INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
                return null;
            });
            insertContacts(r, conn);
            insertSections(r, conn);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.doInTransaction(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            sqlHelper.executeInConn(conn, "SELECT uuid, full_name FROM resume ORDER BY full_name, uuid", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
                return null;
            });
            sqlHelper.executeInConn(conn, "SELECT resume_uuid, type, value FROM contact", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addContact(resume, rs);
                }
                return null;
            });
            sqlHelper.executeInConn(conn, "SELECT resume_uuid, type, value FROM section", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addSection(resume, rs);
                }
                return null;
            });
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count");
        });
    }

    private void deleteContacts(Resume r, Connection conn) {
        sqlHelper.executeInConn(conn, "DELETE FROM contact WHERE resume_uuid = ?", (ps) -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void insertContacts(Resume r, Connection conn) {
        sqlHelper.executeInConn(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", ps -> {
            String uuid = r.getUuid();
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
            return null;
        });
    }

    private void addContact(Resume dst, ResultSet src) throws SQLException {
        String type = src.getString("type");
        String value = src.getString("value");
        if (Objects.isNull(type) || Objects.isNull(value)) return;
        dst.addContact(ContactType.valueOf(type), value);
    }

    private void deleteSections(Resume r, Connection conn) {
        sqlHelper.executeInConn(conn, "DELETE FROM section WHERE resume_uuid = ?", (ps) -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void insertSections(Resume r, Connection conn) {
        sqlHelper.executeInConn(conn, "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)", ps -> {
            String uuid = r.getUuid();
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, e.getKey().name());
                ps.setString(3, mapToString(e.getKey(), e.getValue()));
                ps.addBatch();
            }
            ps.executeBatch();
            return null;
        });
    }

    private void addSection(Resume dst, ResultSet src) throws SQLException {
        String type = src.getString("type");
        String value = src.getString("value");
        if (Objects.isNull(type) || Objects.isNull(value)) return;
        SectionType sectionType = SectionType.valueOf(type);
        dst.addSection(sectionType, mapToSection(sectionType, value));
    }

    private String mapToString(SectionType sectionType, Section section) {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                // TextSection
                return ((TextSection) section).getContent();
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                // ListSection
                return String.join("\n", ((ListSection) section).getItems());
            case EXPERIENCE:
            case EDUCATION:
                // OrganizationSection
                return null;
            default:
                throw new UnsupportedOperationException("Unsupported section type " + sectionType.name());
        }
    }

    private Section mapToSection(SectionType sectionType, String value) {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                // TextSection
                return new TextSection(value);
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                // ListSection
                String[] items = "".equals(value) ? new String[0] : value.split("\n");
                return new ListSection(items);
            case EXPERIENCE:
            case EDUCATION:
                // OrganizationSection
                return null;
            default:
                throw new UnsupportedOperationException("Unsupported section type " + sectionType.name());
        }
    }
}
