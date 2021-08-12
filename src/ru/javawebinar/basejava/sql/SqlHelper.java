package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static ru.javawebinar.basejava.util.StringUtils.substringBetween;


public class SqlHelper {
    public static final String UNIQUE_VIOLATION = "23505";

    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String query, SqlExecutor<T> function) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return function.execute(ps);
        } catch (SQLException e) {
            if (UNIQUE_VIOLATION.equals(e.getSQLState())) {
                throw new ExistStorageException(getUuidFrom(e.getMessage()));
            }
            throw new StorageException(e);
        }
    }

    public void execute(String query) {
        execute(query, PreparedStatement::execute);
    }

    private String getUuidFrom(String errorMessage) {
        // "Подробности: Key (uuid)=(d75bc6c7-863f-4d5e-aff3-cd7db26cf87a) already exists."
        return substringBetween(errorMessage, "(uuid)=(", ") already exists");
    }

}
