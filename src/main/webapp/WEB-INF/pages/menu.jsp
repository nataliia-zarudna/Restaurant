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
    <title>Menu</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="all"/>
    <link rel="stylesheet" href="css/slider-styles.css" type="text/css" media="all"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Libre+Baskerville' rel='stylesheet' type='text/css'>
    <script src="js/menu.js"></script>
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
                <c:forEach var="sectionEntry" items="${menu}">

                    <h4>${sectionEntry.key.title}</h4>
                    <c:forEach var="dish" items="${sectionEntry.value}">
                        <li>
                            <h3>${dish.title}</h3>
                            <img src="images/${dish.icon}">

                            <p>${dish.description}</p>
                            <button>$${dish.price}</button>

                            <sec:authorize access="isAuthenticated()">
                                <div>
                                    <button class="order_btn"
                                            onclick="javascript: location.href = 'orderDish?dishID=${dish.id}'">Order
                                    </button>
                                    <button class="select_order">Select order</button>
                                </div>
                                <ul>
                                    <c:forEach var="order" items="${availableOrders}">
                                        <li onclick="javascript:
                                                location.href='addDishToOrder?orderID=${order.id}&dishID=${dish.id}'">${order.title}</li>
                                    </c:forEach>
                                </ul>
                            </sec:authorize>
                        </li>
                    </c:forEach>

                    <div class="clear"></div>
                </c:forEach>

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
