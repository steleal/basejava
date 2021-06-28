package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    protected Object searchKey(String uuid) {
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            Resume r = storage.get(i);
            if (Objects.equals(r.getUuid(), uuid)) {
                return Integer.valueOf(i);
            }
        }
        return Integer.valueOf(-1);
    }

    protected Resume getElement(Object key) {
        return storage.get((int) key);
    }

    protected void insertElement(Resume r, Object key) {
        storage.add(r);
    }

    protected void updateElement(Resume r, Object key) {
        storage.set((int) key, r);
    }

    protected void deleteElement(Object key) {
        storage.remove((int) key);
    }

    protected void checkExist(String uuid, Object key) {
        int index = (int) key;
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected void checkNotExist(String uuid, Object key) {
        int index = (int) key;
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
    }

}
