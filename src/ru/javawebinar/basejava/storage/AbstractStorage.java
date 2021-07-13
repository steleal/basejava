package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
    private final static Comparator<Resume> FULL_NAME_UUID_COMPARATOR
            = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public void update(Resume r) {
        SK key = searchKey(r.getUuid());
        checkExist(r.getUuid(), key);
        updateElement(r, key);
    }

    public Resume get(String uuid) {
        SK key = searchKey(uuid);
        checkExist(uuid, key);
        return getElement(key);
    }

    public void delete(String uuid) {
        SK key = searchKey(uuid);
        checkExist(uuid, key);
        deleteElement(key);
    }

    public void save(Resume r) {
        SK key = searchKey(r.getUuid());
        checkNotExist(r.getUuid(), key);
        insertElement(r, key);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getListOfResumes();
        resumes.sort(FULL_NAME_UUID_COMPARATOR);
        return resumes;
    }

    protected abstract SK searchKey(String uuid);

    protected abstract Resume getElement(SK key);

    protected abstract void insertElement(Resume r, SK key);

    protected abstract void updateElement(Resume r, SK key);

    protected abstract void deleteElement(SK key);

    protected abstract List<Resume> getListOfResumes();

    protected void checkExist(String uuid, SK key) {
        if (key == null) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected void checkNotExist(String uuid, SK key) {
        if (key != null) {
            throw new ExistStorageException(uuid);
        }
    }

}
