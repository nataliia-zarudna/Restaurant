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
  <title>Groups [Admin]</title>
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
    <jsp:param name="activeMenuItem" value="groupsAdmin" />
  </jsp:include>

  <div class="main-body">

    <jsp:include page="imagesSlider.jsp"/>

    <div class="grids">

      <ul>

        <c:forEach var="group" items="${groups}">

          <h4>${group.group.title}</h4>
          <a href="/deleteGroup?id=${group.group.id}">Delete Group</a>

          <c:forEach var="user" items="${group.users}">
            <li>
              <h3>${user.firstName} ${user.lastName}</h3>
            </li>
          </c:forEach>

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
