package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ListSection;
import ru.javawebinar.basejava.model.Organization;
import ru.javawebinar.basejava.model.Organization.Position;
import ru.javawebinar.basejava.model.OrganizationSection;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.TextSection;
import ru.javawebinar.basejava.util.DateUtil;

import java.time.LocalDate;

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
        Resume resume = createResume("uuid1", "Григорий Кислин");
        System.out.println(resume);
    }

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(PHONE, "+7(921) 855-0482");
        resume.addContact(SKYPE, "grigory.kislin");
        resume.addContact(MAIL, "gkislin@yandex.ru");
        resume.addContact(LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(GITHUB, "https://github.com/gkislin");
        resume.addContact(HOME_PAGE, "http://gkislin.ru/");

        resume.addSection(OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        resume.addSection(PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. " +
                "Пурист кода и архитектуры."));

        resume.addSection(ACHIEVEMENT, new ListSection(
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
        ));

        resume.addSection(QUALIFICATIONS, new ListSection(
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
        ));

        Organization javaOnlineProjects = new Organization("Java Online Projects", "http://javaops.ru/",
                new Position(
                        DateUtil.of(2012, 10),
                        LocalDate.now(),
                        "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."));
        Organization wrike = new Organization("Wrike", "https://www.wrike.com/",
                new Position(
                        DateUtil.of(2014, 10),
                        DateUtil.of(2016, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike " +
                                "(Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        Organization rit = new Organization("RIT Center", "",
                new Position(
                        DateUtil.of(2012, 4),
                        DateUtil.of(2014, 10),
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: " +
                                "релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                                "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                                "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения " +
                                "(почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование " +
                                "из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, " +
                                "Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, " +
                                "Unix shell remote scripting via ssh tunnels, PL/Python"));
        Organization luxoftBank = new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
                new Position(
                        DateUtil.of(2010, 12),
                        DateUtil.of(2012, 4),
                        "Ведущий программист",
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). " +
                                "Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов " +
                                "в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        Organization yota = new Organization("Yota", "https://www.yota.ru/",
                new Position(
                        DateUtil.of(2008, 6),
                        DateUtil.of(2010, 12),
                        "Ведущий специалист",
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                                "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                                "Реализация администрирования, статистики и мониторинга фреймворка. " +
                                "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        Organization enkata = new Organization("Enkata", "http://enkata.com/",
                new Position(
                        DateUtil.of(2007, 3),
                        DateUtil.of(2008, 6),
                        "Разработчик ПО",
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) " +
                                "частей кластерного J2EE приложения (OLAP, Data mining)."));
        Organization siemensAg = new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html",
                new Position(
                        DateUtil.of(2005, 1),
                        DateUtil.of(2007, 2),
                        "Разработчик ПО",
                        "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО " +
                                "на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        Organization alcatel = new Organization("Alcatel", "http://www.alcatel.ru/",
                new Position(
                        DateUtil.of(1997, 9),
                        DateUtil.of(2005, 1),
                        "Инженер по аппаратному и программному тестированию",
                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));

        Organization coursera = new Organization("Coursera", "https://www.coursera.org/course/progfun",
                new Position(
                        DateUtil.of(2013, 3),
                        DateUtil.of(2013, 5),
                        "\"Functional Programming Principles in Scala\" by Martin Odersky", ""));
        Organization luxoftEdu = new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                new Position(
                        DateUtil.of(2011, 3),
                        DateUtil.of(2011, 4),
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", ""));
        Organization siemensEdu = new Organization("Siemens AG", "http://www.siemens.ru/",
                new Position(
                        DateUtil.of(2005, 1),
                        DateUtil.of(2005, 4),
                        "3 месяца обучения мобильным IN сетям (Берлин)", ""));
        Organization alcatelEdu = new Organization("Alcatel", "http://www.alcatel.ru/",
                new Position(
                        DateUtil.of(1997, 9),
                        DateUtil.of(1998, 3),
                        "6 месяцев обучения цифровым телефонным сетям (Москва)", ""));
        Organization itmo = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, " +
                "механики и оптики", "http://www.ifmo.ru/",
                new Position(
                        DateUtil.of(1993, 9),
                        DateUtil.of(1996, 7),
                        "Аспирантура (программист С, С++)", ""),
                new Position(
                        DateUtil.of(1987, 9),
                        DateUtil.of(1993, 7),
                        "Инженер (программист Fortran, C)", ""));
        Organization school = new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/",
                new Position(
                        DateUtil.of(1984, 9),
                        DateUtil.of(1987, 6),
                        "Закончил с отличием", ""));

        resume.addSection(EXPERIENCE, new OrganizationSection(
                javaOnlineProjects, wrike, rit, luxoftBank, yota, enkata, siemensAg, alcatel));

        resume.addSection(EDUCATION, new OrganizationSection(
                coursera, luxoftEdu, siemensEdu, alcatelEdu, itmo, school));

        return resume;
    }
}
