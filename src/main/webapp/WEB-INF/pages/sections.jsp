<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dishes</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

</head>
<body>

    <ul>
        <c:forEach var="section" items="${sections}">
            <li><a href="/section?id=${section.id}">${section.title}</a></li>
        </c:forEach>
    </ul>

    <form action="/addSection" method="post" >

        <input name="title" value="first dishes" />
        <input name="icon" value="first_dishes.png" />
        <input name="description" value="best first dishes" />

        <input type="submit" name="Create Dish" />
    </form>

</body>
</html>
