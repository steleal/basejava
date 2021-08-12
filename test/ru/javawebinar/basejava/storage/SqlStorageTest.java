package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

public class SqlStorageTest extends AbstractStorageTest {
    protected static final SqlStorage sqlStorage = Config.get().getSqlStorage();

    public SqlStorageTest() {
        super(sqlStorage);
    }
}
