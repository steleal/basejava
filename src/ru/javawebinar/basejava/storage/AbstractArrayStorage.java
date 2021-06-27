package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

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
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected Resume getElement(int index) {
        return storage[index];
    }

    protected void insertElement(Resume r, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertResumeToStorage(r, index);
        size++;
    }

    protected void updateElement(Resume r, int index) {
        storage[index] = r;
    }

    protected void deleteElement(int index) {
        fillDeletedElement(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertResumeToStorage(Resume r, int index);

}
