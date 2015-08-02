<%--
  Created by IntelliJ IDEA.
  User: sirobaban
  Date: 28.07.2015
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

  <form action="/addDish" method="post" >

    <input name="sectionID" value="1" />
    <input name="title" value="1" />
    <input name="icon" value="1" />
    <input name="price" value="1" />
    <input name="description" value="1" />
    <input name="isAvailable" value="true" />

    <input type="submit" name="Create Dish" />
  </form>

</body>
</html>
