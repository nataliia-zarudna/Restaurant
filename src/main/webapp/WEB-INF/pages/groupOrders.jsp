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
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <script type="text/javascript" src="js/slider.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Libre+Baskerville' rel='stylesheet' type='text/css'>
    <script src="js/lib/jquery.bpopup.min.js"></script>
    <script src="js/groupOrders.js"></script>
    <link rel="stylesheet" href="css/popup.css" media="screen" type="text/css"/>
    <!--script>
        $(function () {
            $("#datepicker").datepicker();
        });
        $(function () {
            $("#radio").buttonset();
            $('.dishesMode').hide();

            $.ajax({
                url: "groupOrders",
                type: "GET",
                data: ""
            });
        });
    </script-->
</head>
<body>
<div class="wrap">

    <jsp:include page="header.jsp">
        <jsp:param name="activeMenuItem" value="groupOrders"/>
    </jsp:include>

    <div class="main-body">

        <jsp:include page="imagesSlider.jsp"/>

        <div class="lists">

            <div id="radio">
                <label>
                    <input type="radio" id="radio1" name="radio"/>
                    <img src="images/man.png" title="usersMode"
                         onclick="javascript: $('.dishesMode').hide(); $('.usersMode').show();"/>
                </label>
                <label>
                    <input type="radio" id="radio2" name="radio" checked="checked"/>
                    <img src="images/cake.png" title="dishesMode"
                         onclick="javascript: $('.usersMode').hide(); $('.dishesMode').show();"/>
                </label>
            </div>


            <c:choose>
                <c:when test="${empty groupOrderDetailses}">
                    <h4>No Orders</h4>
                </c:when>
                <c:otherwise>

                    <ul>

                        <c:forEach var="orderDetails" items="${groupOrderDetailses}">

                            <h4>
                                <a href="order?id=${orderDetails.order.id}"
                                   class="orderTitle">${orderDetails.order.title}
                                </a>
                                <a href="/cancelOrder?id=${orderDetails.order.id}">
                                    <img src="images/cancel_order.png" title="Cancel Order"/>
                                </a>
                            </h4>

                            <div class="usersMode">
                                <c:forEach var="userOrderedDishes" items="${orderDetails.usersOrderedDetails}">
                                    <h3>${userOrderedDishes.key.firstName} ${userOrderedDishes.key.lastName}</h3>

                                    <table style="width: 100%">
                                        <c:forEach var="orderedDish"
                                                   items="${userOrderedDishes.value.orderedDishes}">
                                            <tr>
                                                <td><p>${orderedDish.dish.title}</p></td>
                                                <td><p>${orderedDish.count}</p></td>
                                                <td><p>$${orderedDish.totalPrice}</p></td>
                                            </tr>
                                        </c:forEach>
                                    </table>

                                    <p>Total &nbsp;&nbsp;<span>${userOrderedDishes.value.totalPrice}</span></p>
                                    <hr/>
                                </c:forEach>
                            </div>
                            <div class="dishesMode">
                                <table style="width: 100%">
                                    <c:forEach var="orderedDish" items="${orderDetails.orderedDishes}">
                                        <tr>
                                            <td><p>${orderedDish.dish.title}</p></td>
                                            <td><p>${orderedDish.count}</p></td>
                                            <td><p>$${orderedDish.totalPrice}</p></td>
                                        </tr>


                                    </c:forEach>
                                </table>
                                <hr/>
                            </div>


                            <p>Total &nbsp;&nbsp;<span>${orderDetails.totalPrice}</span></p>

                            <p>Status &nbsp;&nbsp;<span>${orderDetails.orderStatus}</span></p>

                            <a href="/startOrdering?orderID=${orderDetails.order.id}">Add Dishes</a>
                            <!--a href="/cancelOrder?id=${orderDetails.order.id}">Cancel Order</a>
                            <a href="/checkout?orderID=${orderDetails.order.id}">Checkout</a-->

                            <div class="clear"></div>
                        </c:forEach>

                    </ul>
                </c:otherwise>
            </c:choose>
            <h4><a href="#" class="addGroupOrder"><img src="images/add.png" title="addGroupOrder"/></a></h4>
            <jsp:include page="popups/addGroupOrder.jsp"/>

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
