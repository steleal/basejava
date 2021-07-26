package ru.javawebinar.basejava.storage;


public class ObjectStreamPathStorage extends AbstractPathStorage {
    public ObjectStreamPathStorage(String directory) {
        super(directory, new ObjectStreamSerializer());
    }
}
