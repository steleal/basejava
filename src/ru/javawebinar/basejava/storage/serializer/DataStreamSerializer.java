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
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            writeWithException(r.getContacts().entrySet(), dos, (dos1, entry) -> {
                dos1.writeUTF(entry.getKey().name());
                dos1.writeUTF(entry.getValue());
            });

            writeWithException(r.getSections().entrySet(), dos, (dos1, entry) -> {
                SectionType sectionType = entry.getKey();
                dos1.writeUTF(sectionType.name());
                writeSection(dos1, sectionType, entry.getValue());
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readWithException(dis, (dis1) -> resume.addContact(ContactType.valueOf(dis1.readUTF()), dis1.readUTF()));

            readWithException(dis, (dis1) -> {
                SectionType sectionType = SectionType.valueOf(dis1.readUTF());
                resume.addSection(sectionType, readSection(dis1, sectionType));
            });
            return resume;
        }
    }

    private void writeSection(DataOutputStream dos, SectionType sectionType, Section section) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                // TextSection
                dos.writeUTF(((TextSection) section).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                // ListSection
                writeWithException(((ListSection) section).getItems(), dos, DataOutputStream::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                // OrganizationSection
                writeWithException(((OrganizationSection) section).getOrganizations(), dos, (dos1, organization) -> {
                    Link link = organization.getHomePage();
                    dos1.writeUTF(link.getName());
                    String url = link.getUrl();
                    dos1.writeUTF(url == null ? "" : url);
                    writeWithException(organization.getPositions(), dos1, (dos2, position) -> {
                        writeLocalDate(dos2, position.getStartDate());
                        writeLocalDate(dos2, position.getEndDate());
                        dos2.writeUTF(position.getTitle());
                        String description = position.getDescription();
                        dos2.writeUTF(description == null ? "" : description);
                    });
                });
                break;
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                // TextSection
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                // ListSection
                List<String> items = new ArrayList<>();
                readWithException(dis, (dis1) -> items.add(dis.readUTF()));
                return new ListSection(items);
            case EXPERIENCE:
            case EDUCATION:
                // OrganizationSection
                List<Organization> organizations = new ArrayList<>();
                readWithException(dis, (dis1) -> {
                    String name = dis1.readUTF();
                    String url = dis1.readUTF();
                    url = "".equals(url) ? null : url;
                    Link link = new Link(name, url);
                    List<Position> positions = new ArrayList<>();
                    readWithException(dis1, (dis2) -> {
                        LocalDate startDate = readLocalDate(dis2);
                        LocalDate endDate = readLocalDate(dis2);
                        String title = dis2.readUTF();
                        String description = dis2.readUTF();
                        description = "".equals(description) ? null : description;
                        positions.add(new Position(startDate, endDate, title, description));
                    });
                    organizations.add(new Organization(link, positions));
                });
                return new OrganizationSection(organizations);
            default:
                throw new IllegalStateException("Section not found");
        }
    }

    private LocalDate readLocalDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), dis.readInt());
    }

    private void writeLocalDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
        dos.writeInt(date.getDayOfMonth());
    }

    private <T> void writeWithException(Collection<T> items, DataOutputStream dos, BiConsumerWithIOException<DataOutputStream, T> action)
            throws IOException {
        dos.writeInt(items.size());
        for (T item : items) {
            action.accept(dos, item);
        }
    }

    private void readWithException(DataInputStream dis, ConsumerWithIOException<DataInputStream> action) throws IOException {
        int contSize = dis.readInt();
        for (int i = 0; i < contSize; i++) {
            action.accept(dis);
        }
    }

    @FunctionalInterface
    public interface BiConsumerWithIOException<S, T> {
        void accept(S stream, T item) throws IOException;
    }

    @FunctionalInterface
    public interface ConsumerWithIOException<S> {
        void accept(S stream) throws IOException;
    }
}
