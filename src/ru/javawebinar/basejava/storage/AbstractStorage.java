package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    private final static Comparator<Resume> FULL_NAME_UUID_COMPARATOR
            = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

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

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getListOfResumes();
        Collections.sort(resumes, FULL_NAME_UUID_COMPARATOR);
        return resumes;
    }

    protected abstract Object searchKey(String uuid);

    protected abstract Resume getElement(Object key);

    protected abstract void insertElement(Resume r, Object key);

    protected abstract void updateElement(Resume r, Object key);

    protected abstract void deleteElement(Object key);

    protected abstract List<Resume> getListOfResumes();

    protected void checkExist(String uuid, Object key) {
        if (key == null) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected void checkNotExist(String uuid, Object key) {
        if (key != null) {
            throw new ExistStorageException(uuid);
        }
    }

}
