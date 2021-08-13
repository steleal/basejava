package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.SQLException;

import static ru.javawebinar.basejava.util.StringUtil.substringBetween;

public class ExceptionUtil {
    public static final String UNIQUE_VIOLATION = "23505";

    public static StorageException convertException(SQLException e) {
        if (UNIQUE_VIOLATION.equals(e.getSQLState())) {
            return new ExistStorageException(getUuidFrom(e.getMessage()));
        }
        return new StorageException(e);
    }

    private static String getUuidFrom(String errorMessage) {
        // "Подробности: Key (uuid)=(d75bc6c7-863f-4d5e-aff3-cd7db26cf87a) already exists."
        return substringBetween(errorMessage, "(uuid)=(", ") already exists");
    }
}
