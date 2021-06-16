import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final static int STORAGE_SIZE = 10000;

    private Resume[] storage = new Resume[STORAGE_SIZE];
    private int resumeCnt = 0;

    void clear() {
        // maybe, it is not necessary to null the resume links
        for (int i = 0; i < resumeCnt; i++) {
            storage[i] = null;
        }

        resumeCnt = 0;
    }


    /**
     * @throws RuntimeException if number of resumes will be more than the storage size.
     */
    void save(Resume r) {
        throwExceptionIfStorageFull(String.format("Can not save resume %s: storage is full.", r));
        storage[resumeCnt++] = r;
    }

    /**
     * @return {@link Resume} or null, if uuid not found.
     */
    Resume get(String uuid) {
        int i = indexOf(uuid);
        if (i < 0) {
            return null;
        }
        return storage[i];
    }

    void delete(String uuid) {
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
    Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCnt);
    }

    int size() {
        return resumeCnt;
    }

    private int indexOf(String uuid) {
        for (int i = 0; i < resumeCnt; i++) {
            Resume resume = storage[i];
            if (resume.uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    private void throwExceptionIfStorageFull(String message) {
        if (resumeCnt == STORAGE_SIZE) {
            throw new RuntimeException(message);
        }
    }

}
