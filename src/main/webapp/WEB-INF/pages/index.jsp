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
    <title>Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="all"/>
    <link rel="stylesheet" href="css/slider-styles.css" type="text/css" media="all"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Libre+Baskerville' rel='stylesheet' type='text/css'>
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
                <h4>To day-Items</h4>
                <li>
                    <h3>Ipsum simply</h3>
                    <img src="images/thumb-1.jpg">

                    <p>Neque porro quisquam est, qui dolorem ipsum quia dolor sit ame</p>
                    <button>$12.58</button>
                </li>
                <li>
                    <h3>Lorem Ipsum</h3>
                    <img src="images/thumb-2.jpg">

                    <p>Neque porro quisquam est, qui dolorem ipsum quia dolor sit ame</p>
                    <button>$12.58</button>
                </li>
                <li>
                    <h3>Ipsum simply</h3>
                    <img src="images/thumb-3.jpg">

                    <p>Neque porro quisquam est, qui dolorem ipsum quia dolor sit ame</p>
                    <button>$12.58</button>
                </li>
                <a href="#">View all</a>

                <div class="clear"></div>

                <h4>Latest-Items</h4>
                <li>
                    <h3>Ipsum simply</h3>
                    <img src="images/thumb-5.jpg">

                    <p>Neque porro quisquam est, qui dolorem ipsum quia dolor sit ame</p>
                    <button>$12.58</button>
                </li>
                <li>
                    <h3> Lorem Ipsum</h3>
                    <img src="images/thumb-6.jpg">

                    <p>Neque porro quisquam est, qui dolorem ipsum quia dolor sit ame</p>
                    <button>$12.58</button>
                </li>
                <li class="last">
                    <h3>Lorem simply</h3>
                    <img src="images/thumb-4.jpg">

                    <p>Neque porro quisquam est, qui dolorem ipsum quia dolor sit ame</p>
                    <button>$12.58</button>
                </li>
                <a href="#">View all</a>
            </ul>
            <div class="clear"></div>
        </div>

        <jsp:include page="sideNav.jsp" />

        <div class="clear"></div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
