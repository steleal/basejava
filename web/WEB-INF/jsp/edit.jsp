<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <jsp:useBean id="isNew" type="java.lang.Boolean" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="isNew" value="${isNew}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h2>Контакты:</h2>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <h2>Секции:</h2>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <%--    ВНИМАНИЕ, ГРАБЛИ!!!!
                useBean бросит Exception javax.servlet.ServletException: bean [name] not found within scope,
                если value == null !!!!
                Некуда бежать, придется подменять null на пустые секции, при вызове, т.е. в сервлете.
            --%>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section"/>
            <c:set var="editValue" value="${ section.toString() }"/>
            <jsp:useBean id="editValue" type="java.lang.String"/>
            <dl>
                <h3>${type.title}</h3>
                <dd  class="org">
                    <c:choose>
                        <c:when test="${type=='OBJECTIVE'}">
                            <input type='text' name='${type}' value='<%=editValue%>' size=60>
                        </c:when>
                        <c:when test="${type=='PERSONAL'}">
                            <textarea name='${type}' cols=60 rows=10><%=editValue%></textarea>
                        </c:when>
                        <c:when test="${type=='ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                            <textarea name='${type}' cols=60 rows=10><%=editValue%></textarea>
                        </c:when>
                        <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                            <input type="hidden" name='${type}' value="1">
                            <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizations()%>"
                                       varStatus="counter">
                                <dl>
                                    <dt>Название:</dt>
                                    <dd><input type="text" name='${type}.name' value="${organization.homePage.name}"></dd>
                                </dl>
                                <dl>
                                    <dt>Сайт:</dt>
                                    <dd><input type="text" name='${type}.url' value="${organization.homePage.url}"></dd>
                                    </dd>
                                </dl>
                                <c:forEach var="position" items="${organization.positions}">
                                    <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                                    <div class="date">
                                    <dl>
                                        <dt>Начальная дата:</dt>
                                        <dd>
                                            <input type="text" name="${type}${counter.index}.startDate"
                                                   value="<%=DateUtil.format(position.getStartDate())%>" >
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Конечная дата:</dt>
                                        <dd>
                                            <input type="text" name="${type}${counter.index}.endDate"
                                                   value="<%=DateUtil.format(position.getEndDate())%>" >
                                    </dl>
                                    </div>
                                    <div class = "pos">
                                    <dl>
                                        <dt>Должность:</dt>
                                        <dd><input type="text" name='${type}${counter.index}.title'
                                                   value="${position.title}">
                                    </dl>
                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd><textarea name="${type}${counter.index}.description" rows=10
                                                      >${position.description}</textarea></dd>
                                    </dl>
                                    </div>
                                </c:forEach>
                            </c:forEach>

                        </c:when>
                    </c:choose>
                </dd>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

