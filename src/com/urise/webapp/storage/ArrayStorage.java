package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final static int STORAGE_SIZE = 10000;

    private Resume[] storage = new Resume[STORAGE_SIZE];
    private int resumeCnt = 0;

    public void clear() {
        // maybe, it is not necessary to null the resume links
        for (int i = 0; i < resumeCnt; i++) {
            storage[i] = null;
        }

        resumeCnt = 0;
    }


    /**
     * @throws RuntimeException if number of resumes will be more than the storage size.
     */
    public void save(Resume r) {
        if (resumeCnt == STORAGE_SIZE) {
            throw new RuntimeException(String.format("Can not save resume %s: storage is full.", r));
        }
        storage[resumeCnt++] = r;
    }

    /**
     * @return {@link Resume} or null, if uuid not found.
     */
    public Resume get(String uuid) {
        int index = indexOf(uuid);
        return index < 0 ? null : storage[index];
    }

    public void delete(String uuid) {
        int delIndex = indexOf(uuid);
        if (delIndex < 0) {
            return;
        }

        //move all resumes after deleting one step to the left and clear last element
        int srcIndex = delIndex + 1;
        int length = resumeCnt - srcIndex;
        System.arraycopy(storage, srcIndex, storage, delIndex, length);
        storage[--resumeCnt] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCnt);
    }

    public int size() {
        return resumeCnt;
    }

    private int indexOf(String uuid) {
        for (int i = 0; i < resumeCnt; i++) {
            Resume resume = storage[i];
            if (resume.getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
