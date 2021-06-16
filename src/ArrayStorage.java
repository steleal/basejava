/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final static int STORAGE_SIZE = 10000;

    private Resume[] storage = new Resume[STORAGE_SIZE];
    private int resumeCnt = 0;

    void clear() {
    }

    void save(Resume r) {
        if (resumeCnt == STORAGE_SIZE) {
            throw new RuntimeException("The number of resumes is more than the storage size");
        }
        storage[resumeCnt++] = r;
    }

    Resume get(String uuid) {
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
        return 0;
    }
}
