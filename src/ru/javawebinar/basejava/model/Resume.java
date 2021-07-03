package ru.javawebinar.basejava.model;

import java.util.Comparator;
import java.util.UUID;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume implements Comparable<Resume>{
    private final static Comparator<Resume> FULL_NAME_UUID_COMPARATOR
            = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    // Unique identifier
    private final String uuid;
    private final String fullName;

    public Resume() {
        this(UUID.randomUUID().toString());
    }

    public Resume(String uuid) {
        this.uuid = uuid;
        this.fullName = "";
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (uuid != null ? !uuid.equals(resume.uuid) : resume.uuid != null) return false;
        return fullName != null ? fullName.equals(resume.fullName) : resume.fullName == null;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return uuid + " " + fullName;
    }

    @Override
    public int compareTo(Resume r) {
        return FULL_NAME_UUID_COMPARATOR.compare(this, r);
    }
}
