package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public List<Resume> getAllSorted() {
        return storage.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    protected Object searchKey(String uuid) {
        int size = storage.size();
        for (int i = 0; i < size; i++) {
            Resume r = storage.get(i);
            if (Objects.equals(r.getUuid(), uuid)) {
                return i;
            }
        }
        return null;
    }

    protected Resume getElement(Object key) {
        return storage.get((int) key);
    }

    protected void insertElement(Resume r, Object key) {
        storage.add(r);
    }

    protected void updateElement(Resume r, Object key) {
        storage.set((int) key, r);
    }

    protected void deleteElement(Object key) {
        storage.remove((int) key);
    }

}
