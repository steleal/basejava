package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

// todo удалить после ревью
//  Вот зря мы MapStorage унаследовали от AbstractStorage, надо было implements Storage.
//  А сейчас мы решаем ненужные (т.к. мапа) проблемы по работе с индексом :-(
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
    protected int getIndex(String uuid) {
        Object[] uuids = storage.keySet().toArray();
        for (int i = 0; i < uuids.length; i++) {
            if (Objects.equals(uuid, uuids[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume getElement(int index) {
        Object[] uuids = storage.keySet().toArray();
        return storage.get(uuids[index]);
    }

    @Override
    protected void insertElement(Resume r, int index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateElement(Resume r, int index) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void deleteElement(int index) {
        Object[] uuids = storage.keySet().toArray();
        storage.remove(uuids[index]);
    }

}
