<%--
  Created by IntelliJ IDEA.
  User: sirobaban
  Date: 28.07.2015
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>

    <h5>c <c:out value="${section}" /></h5>
  <h5>${section.id}</h5>
  <h5>${section.title}</h5>
  <h5>${section.icon}</h5>
  <h5>${section.description}</h5>

    <a href="/delete?id=${section.id}">Delete</a>

</body>
</html>
