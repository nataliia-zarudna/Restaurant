<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sirobaban
  Date: 28.07.2015
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML>
<html>
<head>
  <title>Menu</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" href="css/style.css" type="text/css" media="all"/>
  <link rel="stylesheet" href="css/slider-styles.css" type="text/css" media="all"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <script type="text/javascript" src="js/slider.js"></script>
  <link href='http://fonts.googleapis.com/css?family=Libre+Baskerville' rel='stylesheet' type='text/css'>
  <script src="js/lib/jquery.bpopup.min.js"></script>
  <link rel="stylesheet" href="css/popup.css" media="screen" type="text/css" />

</head>
<body>
<div class="wrap">

  <jsp:include page="header.jsp">
    <jsp:param name="activeMenuItem" value="usersAdmin" />
  </jsp:include>

  <div class="main-body">
    <div class="grids">

      <ul>

        <c:forEach var="user" items="${users}">

          <h4>${user.firstName} ${user.lastName}</h4>
          <a href="/deleteUser?id=${user.id}">Delete User</a>

            <li>
              <h3>Phone: ${user.phone}</h3>
              <h3>Email: ${user.email}</h3>

              <!--p>${user.firstName} ${user.lastName}</p-->
              <!--button onclick="location.href='orderDish?dishID=${dish.id}'">Order</button-->
            </li>

          <a href="#">View all</a>
          <div class="clear"></div>
        </c:forEach>

      </ul>
      <div class="clear"></div>
    </div>

    <jsp:include page="sideNav.jsp" />

    <div class="clear"></div>
  </div>
</div>


<jsp:include page="footer.jsp"/>

<jsp:include page="popups/addGroupPopup.jsp"/>

</body>
</html>
