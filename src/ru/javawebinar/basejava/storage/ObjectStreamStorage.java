package ru.javawebinar.basejava.storage;

public class ObjectStreamStorage extends AbstractFileStorage {
    public ObjectStreamStorage(String directory) {
        super(directory, new ObjectStreamSerializer());
    }
}
