package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.Company;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Job;
import ru.javawebinar.basejava.model.ListSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.SimpleLineSection;
import ru.javawebinar.basejava.model.Text;

import java.io.PrintStream;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Map;

import static ru.javawebinar.basejava.model.ContactType.EMAIL;
import static ru.javawebinar.basejava.model.ContactType.GITHUB;
import static ru.javawebinar.basejava.model.ContactType.HOMEPAGE;
import static ru.javawebinar.basejava.model.ContactType.LINKEDIN;
import static ru.javawebinar.basejava.model.ContactType.PHONE;
import static ru.javawebinar.basejava.model.ContactType.SKYPE;
import static ru.javawebinar.basejava.model.SectionType.ACHIEVEMENT;
import static ru.javawebinar.basejava.model.SectionType.EDUCATION;
import static ru.javawebinar.basejava.model.SectionType.EXPERIENCE;
import static ru.javawebinar.basejava.model.SectionType.OBJECTIVE;
import static ru.javawebinar.basejava.model.SectionType.PERSONAL;
import static ru.javawebinar.basejava.model.SectionType.QUALIFICATIONS;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = createKislinResume();
        printResume(resume);
    }

    private static void printResume(Resume resume) {
        PrintStream out = System.out;

        out.println(resume.getFullName());
        out.println();

        Map<ContactType, String> contacts = resume.getContacts();
        for (ContactType contactType : ContactType.values()) {
            String contact = contacts.getOrDefault(contactType, null);
            if (contact == null) continue;
            out.printf("%s: %s%n", contactType.getDescription(), contact);
        }
        out.println();

        Map<SectionType, AbstractSection> sections = resume.getSections();
        for (SectionType sectionType : SectionType.values()) {
            AbstractSection section = sections.getOrDefault(sectionType, null);
            if (section == null) continue;
            out.println(sectionType.getTitle());
            out.println(section);
            out.println();
        }
    }

    private static Resume createKislinResume() {
        Resume resume = new Resume("Григорий Кислин");

        Map<ContactType, String> contacts = resume.getContacts();
        contacts.put(PHONE, "+7(921) 855-0482");
        contacts.put(SKYPE, "grigory.kislin");
        contacts.put(EMAIL, "gkislin@yandex.ru");
        contacts.put(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(GITHUB, "https://github.com/gkislin");
        contacts.put(HOMEPAGE, "http://gkislin.ru/");

        Map<SectionType, AbstractSection> sections = resume.getSections();
        sections.put(OBJECTIVE, new SimpleLineSection<>(new Text("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям")));

        sections.put(PERSONAL, new SimpleLineSection<>(new Text("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры.")));

        sections.put(ACHIEVEMENT, new ListSection<>(Arrays.asList(
                new Text("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven." +
                        " Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                        "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников."),
                new Text("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                        "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."),
                new Text("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                        "Интеграция с 1С, Bonita BPM, CMIS, LDAP. " +
                        "Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                        "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."),
                new Text("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, " +
                        "Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга."),
                new Text("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                        "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
                        "Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. " +
                        "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django)."),
                new Text("Реализация протоколов по приему платежей всех основных платежных системы России " +
                        "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.")
        )));

        sections.put(QUALIFICATIONS, new ListSection<>(Arrays.asList(
                new Text("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2"),
                new Text("Version control: Subversion, Git, Mercury, ClearCase, Perforce"),
                new Text("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle"),
                new Text("MySQL, SQLite, MS SQL, HSQLDB"),
                new Text("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy"),
                new Text("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts"),
                new Text("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring " +
                        "(MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), " +
                        "Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements)"),
                new Text("Python: Django"),
                new Text("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js"),
                new Text("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka"),
                new Text("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, " +
                        "MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT"),
                new Text("Инструменты: Maven + plugin development, Gradle, настройка Ngnix"),
                new Text("Администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer"),
                new Text("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных " +
                        "шаблонов, UML, функционального программирования"),
                new Text("Родной русский, английский \"upper intermediate\"")
        )));

        // alcatel, itmo - встречается дважды
        Company alcatel = new Company("Alcatel", "http://www.alcatel.ru/");
        Company itmo = new Company("Санкт-Петербургский национальный исследовательский университет информационных технологий, " +
                "механики и оптики", "http://www.ifmo.ru/");

        sections.put(EXPERIENCE, new ListSection<>(Arrays.asList(
                new Job(new Company("Java Online Projects", "http://javaops.ru/"),
                        YearMonth.of(2013, 10),
                        YearMonth.now(),
                        "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."),
                new Job(new Company("Wrike", "https://www.wrike.com/"),
                        YearMonth.of(2014, 10),
                        YearMonth.of(2016, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."),
                new Job(new Company("RIT Center", ""),
                        YearMonth.of(2012, 4),
                        YearMonth.of(2014, 10),
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: " +
                                "релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                                "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                                "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения " +
                                "(почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование " +
                                "из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, " +
                                "Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                                "Unix shell remote scripting via ssh tunnels, PL/Python"),
                new Job(new Company("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/"),
                        YearMonth.of(2010, 12),
                        YearMonth.of(2012, 4),
                        "Ведущий программист",
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). " +
                                "Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов " +
                                "в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."),
                new Job(new Company("Yota", "https://www.yota.ru/"),
                        YearMonth.of(2008, 6),
                        YearMonth.of(2010, 12),
                        "Ведущий специалист",
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                                "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                                "Реализация администрирования, статистики и мониторинга фреймворка. " +
                                "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"),
                new Job(new Company("Enkata", "http://enkata.com/"),
                        YearMonth.of(2007, 3),
                        YearMonth.of(2008, 6),
                        "Разработчик ПО",
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                                "частей кластерного J2EE приложения (OLAP, Data mining)."),
                new Job(new Company("Siemens AG", "https://www.siemens.com/ru/ru/home.html"),
                        YearMonth.of(2005, 1),
                        YearMonth.of(2007, 2),
                        "Разработчик ПО",
                        "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО " +
                                "на мобильной IN платформе Siemens @vantage (Java, Unix)."),
                new Job(alcatel,
                        YearMonth.of(1997, 9),
                        YearMonth.of(2005, 1),
                        "Инженер по аппаратному и программному тестированию",
                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")
        )));

        sections.put(EDUCATION, new ListSection<>(Arrays.asList(
                new Job(new Company("Coursera", "https://www.coursera.org/course/progfun"),
                        YearMonth.of(2013, 3),
                        YearMonth.of(2013, 5),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky", ""),
                new Job(new Company("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"),
                        YearMonth.of(2011, 3),
                        YearMonth.of(2011, 4),
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", ""),
                new Job(new Company("Siemens AG", "http://www.siemens.ru/"),
                        YearMonth.of(2005, 1),
                        YearMonth.of(2005, 4),
                        "3 месяца обучения мобильным IN сетям (Берлин)", ""),
                new Job(alcatel,
                        YearMonth.of(1997, 9),
                        YearMonth.of(1998, 3),
                        "6 месяцев обучения цифровым телефонным сетям (Москва)", ""),
                new Job(itmo,
                        YearMonth.of(1993, 9),
                        YearMonth.of(1996, 7),
                        "Аспирантура (программист С, С++)", ""),
                new Job(itmo,
                        YearMonth.of(1987, 9),
                        YearMonth.of(1993, 7),
                        "Инженер (программист Fortran, C)", ""),
                new Job(new Company("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/"),
                        YearMonth.of(1984, 9),
                        YearMonth.of(1987, 6),
                        "Закончил с отличием", "")
        )));
        return resume;
    }
}
