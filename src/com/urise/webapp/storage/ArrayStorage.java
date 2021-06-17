package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final static int STORAGE_SIZE = 10000;

    private Resume[] storage = new Resume[STORAGE_SIZE];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void update(Resume r) {
        // TODO check if resume present, then sout ERROR:
    }

    /**
     * @throws RuntimeException if number of resumes will be more than the storage size.
     */
    public void save(Resume r) {
        // TODO check if resume not present
        storage[size++] = r;
    }

    /**
     * @return {@link Resume} or null, if uuid not found.
     */
    public Resume get(String uuid) {
        int index = indexOf(uuid);
        return index < 0 ? null : storage[index];
    }

    public void delete(String uuid) {
        // TODO check if resume present
        int delIndex = indexOf(uuid);
        if (delIndex < 0) {
            return;
        }

        //order of elements is not important, move only last element
        int lastIndex = --size;
        storage[delIndex] = storage[lastIndex];
        storage[lastIndex] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            Resume resume = storage[i];
            if (resume.getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
