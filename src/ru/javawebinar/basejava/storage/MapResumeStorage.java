package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected Resume searchKey(String uuid) {
        return storage.getOrDefault(uuid, null);
    }

    @Override
    protected Resume getElement(Resume resume) {
        return resume;
    }

    @Override
    protected void insertElement(Resume r, Resume resume) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateElement(Resume r, Resume resume) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void deleteElement(Resume resume) {
        storage.remove(resume.getUuid());
    }

    @Override
    protected List<Resume> getListOfResumes() {
        return new ArrayList<>(storage.values());
    }

}
