<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
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
            --%>
            <%--  <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section"/> --%>
            <c:set var="editValue" value="${section!=null ? section.toString() : ''}"/>
            <jsp:useBean id="editValue" type="java.lang.String"/>
            <dl>
                <h3>${type.title}</h3>
                <dd>
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
                            <textarea name='${type}' cols=60 rows=10><%=editValue%></textarea>
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

