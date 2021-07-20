package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getListOfResumes() {
        return new ArrayList<>(storage);
    }

    protected Integer getSearchKey(String uuid) {
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            Resume r = storage.get(i);
            if (Objects.equals(r.getUuid(), uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected Resume doGet(Integer key) {
        return storage.get(key);
    }

    protected void doSave(Resume r, Integer key) {
        storage.add(r);
    }

    protected void doUpdate(Resume r, Integer key) {
        storage.set(key, r);
    }

    protected void doDelete(Integer key) {
        storage.remove((int) key);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }
}
