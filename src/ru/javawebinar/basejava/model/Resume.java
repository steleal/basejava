package ru.javawebinar.basejava.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * ru.javawebinar.basejava.model.Resume class
 */
public class Resume {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private final Map<ContactType, String> contacts;
    private final Map<SectionType, AbstractSection> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        this.contacts = new EnumMap<>(ContactType.class);
        this.sections = new EnumMap<>(SectionType.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contacts, resume.contacts) &&
                Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(fullName).append("\n");

        for (ContactType contactType : ContactType.values()) {
            String contact = contacts.getOrDefault(contactType, null);
            if (contact == null) continue;
            builder.append(String.format("%s: %s%n", contactType.getTitle(), contact));
        }
        builder.append("\n");

        for (SectionType sectionType : SectionType.values()) {
            AbstractSection section = sections.getOrDefault(sectionType, null);
            if (section == null) continue;
            builder.append(sectionType.getTitle()).append("\n");
            builder.append(section).append("\n\n");
        }
        return builder.toString();
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }
}
