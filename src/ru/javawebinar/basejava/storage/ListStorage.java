package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage {
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
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    protected int getIndex(String uuid) {
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            Resume r = storage.get(i);
            if (Objects.equals(r.getUuid(), uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected Resume getElement(int index) {
        return storage.get(index);
    }

    protected void insertElement(Resume r, int index) {
        storage.add(r);
    }

    protected void updateElement(Resume r, int index) {
        storage.set(index, r);
    }

    protected void deleteElement(int index) {
        storage.remove(index);
    }

}
