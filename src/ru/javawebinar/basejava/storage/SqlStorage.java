package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
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
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                "SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "  ON r.uuid = c.resume_uuid  " +
                        " WHERE r.uuid =?", ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(resume, rs);
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        sqlHelper.doInTransaction(conn -> {
            sqlHelper.execute(conn, "UPDATE resume SET full_name = ? WHERE uuid = ?", (ps) -> {
                ps.setString(1, r.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) throw new NotExistStorageException(uuid);
                return null;
            });
            sqlHelper.execute(conn, "DELETE FROM contact WHERE resume_uuid = ?", (ps) -> {
                ps.setString(1, uuid);
                ps.executeUpdate();
                return null;
            });
            insertContacts(r, conn);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.doInTransaction(conn -> {
            sqlHelper.execute(conn, "INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
                return null;
            });
            insertContacts(r, conn);
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
            sqlHelper.execute(conn, "SELECT uuid, full_name FROM resume ORDER BY full_name, uuid", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
                return null;
            });
            sqlHelper.execute(conn, "SELECT resume_uuid, type, value FROM contact", ps -> {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addContact(resume, rs);
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

    private void insertContacts(Resume r, Connection conn) {
        sqlHelper.execute(conn, "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", ps -> {
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
}
