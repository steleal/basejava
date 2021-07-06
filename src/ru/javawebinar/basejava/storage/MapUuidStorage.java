package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MapUuidStorage extends AbstractStorage {
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
    protected Object searchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume getElement(Object uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void insertElement(Resume r, Object uuid) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateElement(Resume r, Object uuid) {
        storage.put((String) uuid, r);
    }

    @Override
    protected void deleteElement(Object uuid) {
        storage.remove(uuid);
    }

    @Override
    protected List<Resume> getListOfResumes() {
        return storage.values().stream().collect(Collectors.toList());
    }

}
