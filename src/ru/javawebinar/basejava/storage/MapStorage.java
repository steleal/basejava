package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
    public List<Resume> getAllSorted() {
        return storage.values().stream()
                .sorted()
                .collect(Collectors.toList());
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
