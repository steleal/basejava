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
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        int index = indexOf(r.getUuid());
        if (index < 0) {
            System.out.printf("ERROR: resume %s is not found in the storage.%n", r);
        } else {
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        int index = indexOf(r.getUuid());
        if (index >= 0) {
            System.out.printf("ERROR: resume %s already exists in the storage.%n", r);
        } else if (size == STORAGE_SIZE) {
            System.out.printf("ERROR: can not save resume %s, the storage is full.%n", r);
        } else {
            storage[size++] = r;
        }
    }

    /**
     * @return {@link Resume} or null, if uuid not found.
     */
    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            System.out.printf("ERROR: resume with uuid %s is not found in the storage.%n", uuid);
        }
        return index < 0 ? null : storage[index];
    }

    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            System.out.printf("ERROR: resume with uuid %s is not found in the storage.%n", uuid);
        } else {
            //order of elements is not important, move only last element
            storage[index] = storage[--size];
            storage[size] = null;
        }
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
