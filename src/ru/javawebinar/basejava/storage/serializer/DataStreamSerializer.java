package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Link;
import ru.javawebinar.basejava.model.ListSection;
import ru.javawebinar.basejava.model.Organization;
import ru.javawebinar.basejava.model.Organization.Position;
import ru.javawebinar.basejava.model.OrganizationSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.TextSection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                writeSection(dos, entry.getValue());
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int contSize = dis.readInt();
            for (int i = 0; i < contSize; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectSize = dis.readInt();
            for (int i = 0; i < sectSize; i++) {
                resume.addSection(SectionType.valueOf(dis.readUTF()), readSection(dis));
            }
            return resume;
        }
    }

    private void writeSection(DataOutputStream dos, Section section) throws IOException {
        dos.writeUTF(section.getClass().getName());
        Class<? extends Section> clazz = section.getClass();
        if (clazz == TextSection.class) {
            TextSection textSection = (TextSection) section;
            dos.writeUTF(textSection.getContent());
        }
        if (clazz == ListSection.class) {
            ListSection listSection = (ListSection) section;
            List<String> items = listSection.getItems();
            dos.writeInt(items.size());
            for (String item : items) {
                dos.writeUTF(item);
            }
        }
        if (clazz == OrganizationSection.class) {
            OrganizationSection organizationSection = (OrganizationSection) section;
            List<Organization> organizations = organizationSection.getOrganizations();
            dos.writeInt(organizations.size());
            for (Organization organization : organizations) {
                Link link = organization.getHomePage();
                dos.writeUTF(link.getName());
                dos.writeUTF(link.getUrl());
                List<Position> positions = organization.getPositions();
                dos.writeInt(positions.size());
                for (Position position : positions) {
                    dos.writeUTF(position.getStartDate().toString());
                    dos.writeUTF(position.getEndDate().toString());
                    dos.writeUTF(position.getTitle());
                    dos.writeUTF(position.getDescription());
                }
            }
        }
    }

    private Section readSection(DataInputStream dis) throws IOException {
        Class<?> clazz;
        try {
            clazz = Class.forName(dis.readUTF());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

        if (clazz == TextSection.class) {
            return new TextSection(dis.readUTF());
        }

        if (clazz == ListSection.class) {
            List<String> items = new ArrayList<>();
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                items.add(dis.readUTF());
            }
            return new ListSection(items);
        }
        
        if (clazz == OrganizationSection.class) {
            List<Organization> organizations = new ArrayList<>();
            int orgSize = dis.readInt();
            for (int i = 0; i < orgSize; i++) {
                Link link = new Link(dis.readUTF(), dis.readUTF());
                List<Position> positions = new ArrayList<>();
                int posSize = dis.readInt();
                for (int j = 0; j < posSize; j++) {
                    LocalDate startDate = LocalDate.parse(dis.readUTF());
                    LocalDate endDate = LocalDate.parse(dis.readUTF());
                    String title = dis.readUTF();
                    String description = dis.readUTF();
                    positions.add(new Position(startDate, endDate, title, description));
                }
                organizations.add(new Organization(link, positions));
            }
            return new OrganizationSection(organizations);
        }

        throw new IllegalStateException("Section not found");
    }

}
