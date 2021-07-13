package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapUuidStorage extends AbstractStorage<String> {
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
    protected String searchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected Resume getElement(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void insertElement(Resume r, String uuid) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateElement(Resume r, String uuid) {
        storage.put(uuid, r);
    }

    @Override
    protected void deleteElement(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected List<Resume> getListOfResumes() {
        return new ArrayList<>(storage.values());
    }

}
