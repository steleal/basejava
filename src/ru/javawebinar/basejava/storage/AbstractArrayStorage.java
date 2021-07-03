package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public List<Resume> getAllSorted() {
        List<Resume> resumes = Arrays.asList(Arrays.copyOfRange(storage, 0, size));
        Collections.sort(resumes);
        return resumes;
    }

    protected Resume getElement(Object key) {
        return storage[(int) key];
    }

    protected void insertElement(Resume r, Object key) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertResumeToStorage(r, (int) key);
        size++;
    }

    protected void updateElement(Resume r, Object key) {
        storage[(int) key] = r;
    }

    protected void deleteElement(Object key) {
        fillDeletedElement((int) key);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertResumeToStorage(Resume r, int index);

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
