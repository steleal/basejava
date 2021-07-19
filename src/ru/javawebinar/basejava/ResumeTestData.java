package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.ListSection;
import ru.javawebinar.basejava.model.Organization;
import ru.javawebinar.basejava.model.OrganizationSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.model.TextSection;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Map;

import static ru.javawebinar.basejava.model.ContactType.GITHUB;
import static ru.javawebinar.basejava.model.ContactType.HOME_PAGE;
import static ru.javawebinar.basejava.model.ContactType.LINKEDIN;
import static ru.javawebinar.basejava.model.ContactType.MAIL;
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
        System.out.println(resume);
    }

    private static Resume createKislinResume() {
        Resume resume = new Resume("Григорий Кислин");

        Map<ContactType, String> contacts = resume.getContacts();
        contacts.put(PHONE, "+7(921) 855-0482");
        contacts.put(SKYPE, "grigory.kislin");
        contacts.put(MAIL, "gkislin@yandex.ru");
        contacts.put(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(GITHUB, "https://github.com/gkislin");
        contacts.put(HOME_PAGE, "http://gkislin.ru/");

        Map<SectionType, AbstractSection> sections = resume.getSections();
        sections.put(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        sections.put(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры."));

        sections.put(ACHIEVEMENT, new ListSection(Arrays.asList(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven." +
                        " Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". " +
                        "Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                        "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                        "Интеграция с 1С, Bonita BPM, CMIS, LDAP. " +
                        "Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. " +
                        "Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, " +
                        "Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                        "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
                        "Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. " +
                        "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России " +
                        "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        )));

        sections.put(QUALIFICATIONS, new ListSection(Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring " +
                        "(MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT, " +
                        "Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements)",
                "Python: Django",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, " +
                        "MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix",
                "Администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных " +
                        "шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\""
        )));

        sections.put(EXPERIENCE, new OrganizationSection(Arrays.asList(
                new Organization("Java Online Projects", "http://javaops.ru/",
                        YearMonth.of(2013, 10).atDay(1),
                        LocalDate.now(),
                        "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."),
                new Organization("Wrike", "https://www.wrike.com/",
                        YearMonth.of(2014, 10).atDay(1),
                        YearMonth.of(2016, 1).atEndOfMonth(),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."),
                new Organization("RIT Center", "",
                        YearMonth.of(2012, 4).atDay(1),
                        YearMonth.of(2014, 10).atEndOfMonth(),
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: " +
                                "релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                                "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                                "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения " +
                                "(почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование " +
                                "из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, " +
                                "Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                                "Unix shell remote scripting via ssh tunnels, PL/Python"),
                new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                        YearMonth.of(2010, 12).atDay(1),
                        YearMonth.of(2012, 4).atEndOfMonth(),
                        "Ведущий программист",
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). " +
                                "Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов " +
                                "в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."),
                new Organization("Yota", "https://www.yota.ru/",
                        YearMonth.of(2008, 6).atDay(1),
                        YearMonth.of(2010, 12).atEndOfMonth(),
                        "Ведущий специалист",
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                                "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                                "Реализация администрирования, статистики и мониторинга фреймворка. " +
                                "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"),
                new Organization("Enkata", "http://enkata.com/",
                        YearMonth.of(2007, 3).atDay(1),
                        YearMonth.of(2008, 6).atEndOfMonth(),
                        "Разработчик ПО",
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                                "частей кластерного J2EE приложения (OLAP, Data mining)."),
                new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html",
                        YearMonth.of(2005, 1).atDay(1),
                        YearMonth.of(2007, 2).atEndOfMonth(),
                        "Разработчик ПО",
                        "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО " +
                                "на мобильной IN платформе Siemens @vantage (Java, Unix)."),
                new Organization("Alcatel", "http://www.alcatel.ru/",
                        YearMonth.of(1997, 9).atDay(1),
                        YearMonth.of(2005, 1).atEndOfMonth(),
                        "Инженер по аппаратному и программному тестированию",
                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")
        )));

        sections.put(EDUCATION, new OrganizationSection(Arrays.asList(
                new Organization("Coursera", "https://www.coursera.org/course/progfun",
                        YearMonth.of(2013, 3).atDay(1),
                        YearMonth.of(2013, 5).atEndOfMonth(),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky", ""),
                new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                        YearMonth.of(2011, 3).atDay(1),
                        YearMonth.of(2011, 4).atEndOfMonth(),
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", ""),
                new Organization("Siemens AG", "http://www.siemens.ru/",
                        YearMonth.of(2005, 1).atDay(1),
                        YearMonth.of(2005, 4).atEndOfMonth(),
                        "3 месяца обучения мобильным IN сетям (Берлин)", ""),
                new Organization("Alcatel", "http://www.alcatel.ru/",
                        YearMonth.of(1997, 9).atDay(1),
                        YearMonth.of(1998, 3).atEndOfMonth(),
                        "6 месяцев обучения цифровым телефонным сетям (Москва)", ""),
                new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, " +
                        "механики и оптики", "http://www.ifmo.ru/",
                        YearMonth.of(1993, 9).atDay(1),
                        YearMonth.of(1996, 7).atEndOfMonth(),
                        "Аспирантура (программист С, С++)", ""),
                new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, " +
                        "механики и оптики", "http://www.ifmo.ru/",
                        YearMonth.of(1987, 9).atDay(1),
                        YearMonth.of(1993, 7).atEndOfMonth(),
                        "Инженер (программист Fortran, C)", ""),
                new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/",
                        YearMonth.of(1984, 9).atDay(1),
                        YearMonth.of(1987, 6).atEndOfMonth(),
                        "Закончил с отличием", "")
        )));
        return resume;
    }
}
