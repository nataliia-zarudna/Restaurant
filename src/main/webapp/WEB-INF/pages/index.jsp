<%@ page import="java.util.Enumeration" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <title>Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="all"/>
    <link rel="stylesheet" href="css/slider-styles.css" type="text/css" media="all"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href='http://fonts.googleapis.com/css?family=Libre+Baskerville' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

    <script src="js/lib/jquery.bpopup.min.js"></script>
    <script src="js/popup.js"></script>
    <link rel="stylesheet" href="css/popup.css" media="screen" type="text/css"/>
    <link rel='stylesheet prefetch' href='css/lib/font-awesome.min.css'>

</head>
<body>
<div class="wrap">

    <jsp:include page="header.jsp">
        <jsp:param name="activeMenuItem" value="home" />
    </jsp:include>

    <div class="main-body">

        <jsp:include page="imagesSlider.jsp" />

        <div class="grids">
            <ul>

                <div class="about-grid1">

                    <li>
                        <h3>Restaurants Hours</h3>
                        <h4 class="dinner">Breakfast </h4>

                        <p>Monday - Friday &nbsp;&nbsp; 11 am - 03 pm</p>

                        <p>Saturaday - Sunday &nbsp;&nbsp; 11 am - 04 pm</p>
                        <h4 class="dinner">Lunch </h4>

                        <p>Monday - Friday &nbsp;&nbsp; 11 am - 03 pm</p>

                        <p>Saturaday - Sunday &nbsp;&nbsp; 11 am - 04 pm</p>
                    </li>
                    <img src="images/about-1.jpg" title="img1"/>
                    <div class="clear"> </div>
                </div>
                <li>
                    <img src="images/thumb-5.jpg" title="img1"/>
                    <div class="clear"> </div>
                </li>
                <li>
                    <img src="images/thumb-6.jpg" title="img1"/>
                    <div class="clear"> </div>
                </li>
                <li>
                    <img src="images/thumb-7.jpg" title="img1"/>
                    <div class="clear"> </div>
                </li>
                <div class="clear"> </div>
                <br>
            </ul>
            <div class="clear"> </div>
        </div>

        <jsp:include page="sideNav.jsp" />

        <div class="clear"></div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
