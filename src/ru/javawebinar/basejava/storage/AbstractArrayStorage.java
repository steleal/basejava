package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected List<Resume> getListOfResumes() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected Resume getElement(Integer key) {
        return storage[key];
    }

    protected void insertElement(Resume r, Integer key) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertResumeToStorage(r, key);
        size++;
    }

    protected void updateElement(Resume r, Integer key) {
        storage[key] = r;
    }

    protected void deleteElement(Integer key) {
        fillDeletedElement(key);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertResumeToStorage(Resume r, int index);

    protected void checkExist(String uuid, Integer key) {
        int index = key;
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected void checkNotExist(String uuid, Integer key) {
        int index = key;
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
    }
}
