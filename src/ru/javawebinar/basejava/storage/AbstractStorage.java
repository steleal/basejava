package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object key = searchKey(r.getUuid());
        checkExist(r.getUuid(), key);
        updateElement(r, key);
    }

    public Resume get(String uuid) {
        Object key = searchKey(uuid);
        checkExist(uuid, key);
        return getElement(key);
    }

    public void delete(String uuid) {
        Object key = searchKey(uuid);
        checkExist(uuid, key);
        deleteElement(key);
    }

    public void save(Resume r) {
        Object key = searchKey(r.getUuid());
        checkNotExist(r.getUuid(), key);
        insertElement(r, key);
    }

    protected abstract Object searchKey(String uuid);

    protected abstract Resume getElement(Object key);

    protected abstract void insertElement(Resume r, Object key);

    protected abstract void updateElement(Resume r, Object key);

    protected abstract void deleteElement(Object key);

    protected abstract void checkExist(String uuid, Object key);

    protected abstract void checkNotExist(String uuid, Object key);

}
