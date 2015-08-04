<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
  <title>Unauthorized Error</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <link rel="stylesheet" href="css/style.css" type="text/css" media="all"/>
  <link rel="stylesheet" href="css/slider-styles.css" type="text/css" media="all"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <link href='http://fonts.googleapis.com/css?family=Libre+Baskerville' rel='stylesheet' type='text/css'>
  <style>
    .ui-menu {
      position: absolute;
      width: 100px;
    }
  </style>
</head>
<body>
<div class="wrap">

  <jsp:include page="header.jsp">
    <jsp:param name="activeMenuItem" value="menu"/>
  </jsp:include>

  <div class="main-body">

    <jsp:include page="imagesSlider.jsp"/>

    <div class="grids">

      <ul>
        <h4>You are not authorized to perform this operation</h4>
        <li>
          <p>Please, <a href="/" style="float: none;">login</a></p>
        </li>

        <div class="clear"></div>
      </ul>
      <div class="clear"></div>
    </div>

    <jsp:include page="sideNav.jsp"/>

    <div class="clear"></div>
  </div>
</div>

<jsp:include page="footer.jsp"/>

<div class="clear">
</div>
</div>

</body>
</html>
