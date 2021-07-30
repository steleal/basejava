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
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                writeSection(dos, sectionType, entry.getValue());
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
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            }
            return resume;
        }
    }

    private void writeSection(DataOutputStream dos, SectionType sectionType, Section section) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                // TextSection
                TextSection textSection = (TextSection) section;
                dos.writeUTF(textSection.getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                // ListSection
                ListSection listSection = (ListSection) section;
                List<String> items = listSection.getItems();
                dos.writeInt(items.size());
                for (String item : items) {
                    dos.writeUTF(item);
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                // OrganizationSection
                OrganizationSection organizationSection = (OrganizationSection) section;
                List<Organization> organizations = organizationSection.getOrganizations();
                dos.writeInt(organizations.size());
                for (Organization organization : organizations) {
                    Link link = organization.getHomePage();
                    dos.writeUTF(link.getName());
                    String url = link.getUrl();
                    dos.writeUTF(url == null ? "" : url);
                    List<Position> positions = organization.getPositions();
                    dos.writeInt(positions.size());
                    for (Position position : positions) {
                        writeLocalDate(dos, position.getStartDate());
                        writeLocalDate(dos, position.getEndDate());
                        dos.writeUTF(position.getTitle());
                        String description = position.getDescription();
                        dos.writeUTF(description == null ? "" : description);
                    }
                }
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
                int size = dis.readInt();
                for (int i = 0; i < size; i++) {
                    items.add(dis.readUTF());
                }
                return new ListSection(items);
            case EXPERIENCE:
            case EDUCATION:
                // OrganizationSection
                List<Organization> organizations = new ArrayList<>();
                int orgSize = dis.readInt();
                for (int i = 0; i < orgSize; i++) {
                    String name = dis.readUTF();
                    String url = dis.readUTF();
                    url = "".equals(url) ? null : url;
                    Link link = new Link(name, url);
                    List<Position> positions = new ArrayList<>();
                    int posSize = dis.readInt();
                    for (int j = 0; j < posSize; j++) {
                        LocalDate startDate = readLocalDate(dis);
                        LocalDate endDate = readLocalDate(dis);
                        String title = dis.readUTF();
                        String description = dis.readUTF();
                        description = "".equals(description) ? null : description;
                        positions.add(new Position(startDate, endDate, title, description));
                    }
                    organizations.add(new Organization(link, positions));
                }
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

}
