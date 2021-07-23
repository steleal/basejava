package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private final static Comparator<Resume> FULL_NAME_UUID_COMPARATOR
            = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    //    protected final Logger log = Logger.getLogger(getClass().getName());
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumes = doCopyAll();
        resumes.sort(FULL_NAME_UUID_COMPARATOR);
        return resumes;
    }

    public void update(Resume r) {
        LOG.info("Update " + r);
        SK key = getExistedSearchKey(r.getUuid());
        doUpdate(r, key);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK key = getExistedSearchKey(uuid);
        return doGet(key);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK key = getExistedSearchKey(uuid);
        doDelete(key);
    }

    public void save(Resume r) {
        LOG.info("Save " + r);
        SK key = getNotExistedSearchKey(r.getUuid());
        doSave(r, key);
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract Resume doGet(SK key);

    protected abstract void doSave(Resume r, SK key);

    protected abstract void doUpdate(Resume r, SK key);

    protected abstract void doDelete(SK key);

    protected abstract List<Resume> doCopyAll();

}
