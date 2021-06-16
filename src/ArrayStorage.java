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

    void save(Resume r) {
        if (resumeCnt == STORAGE_SIZE) {
            throw new RuntimeException("The number of resumes is more than the storage size");
        }
        storage[resumeCnt++] = r;
    }

    Resume get(String uuid) {
        int i = getStorageIndex(uuid);
        if (i > -1) {
            return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return new Resume[0];
    }

    int size() {
        return resumeCnt;
    }

    private int getStorageIndex(String uuid) {
        for (int i = 0; i < resumeCnt; i++) {
            Resume resume = storage[i];
            if (resume.uuid.equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

}
