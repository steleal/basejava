package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Map;
import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new TreeMap<>();

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
        int size = storage.size();
        return storage.values().toArray(new Resume[size]);
    }

    @Override
    protected Object searchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume getElement(Object key) {
        return storage.get(key);
    }

    @Override
    protected void insertElement(Resume r, Object key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateElement(Resume r, Object key) {
        storage.put((String) key, r);
    }

    @Override
    protected void deleteElement(Object key) {
        storage.remove(key);
    }

}
