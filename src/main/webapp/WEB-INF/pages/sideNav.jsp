<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="boxes">
    <div class="order">
        <ul>
            <li>

                <!--h3>ORDER <c:if test="${not empty currentOrderDetails}"><a
                        href="order?id=${currentOrderDetails.order.id}">[${currentOrderDetails.order.title}]</a></c:if>
                </h3-->
                <c:choose>
                    <c:when test="${not empty currentOrderDetails}">
                        <a href="order?id=${currentOrderDetails.order.id}">
                            <h3>${currentOrderDetails.order.title}</h3>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <h3>ORDER </h3>
                    </c:otherwise>
                </c:choose>


                <sec:authorize var="isAuthenticated" access="isAuthenticated()"/>

                <c:choose>
                    <c:when test="${isAuthenticated}">

                        <c:choose>
                            <c:when test="${empty currentOrderDetails}">
                                <h4>No Products</h4>
                            </c:when>
                            <c:otherwise>

                                <c:forEach var="orderedDish" items="${currentOrderDetails.orderedDishes}">
                                    <p>${orderedDish.dish.title}
                                        &nbsp;&nbsp;<span>${orderedDish.count}</span>
                                        &nbsp;&nbsp;<span>${orderedDish.totalPrice}</span></p>
                                </c:forEach>

                                <p>Total &nbsp;&nbsp;<span>${currentOrderDetails.totalPrice}</span></p>

                                <!--h6><a href="/checkout?orderID=${currentOrderDetails.order.id}">Check-out</a></h6-->
                                <h6>
                                    <a href="/cancelOrder?id=${currentOrderDetails.order.id}" class="orderBtn">Cancel Order</a>
                                </h6>
                            </c:otherwise>
                        </c:choose>

                    </c:when>
                    <c:otherwise>
                        <h4>You are not authorized</h4>
                    </c:otherwise>
                </c:choose>
            </li>
        </ul>
    </div>
    <div class="clear"></div>
    <ul>
        <li>
            <h3>Restaurants Hours</h3>
            <h4>Breakfast </h4>

            <p>Monday - Friday &nbsp;&nbsp; 11 am - 03 pm</p>

            <p>Saturaday - Sunday &nbsp;&nbsp; 11 am - 04 pm</p>
            <h4>Lunch </h4>

            <p>Monday - Friday &nbsp;&nbsp; 11 am - 03 pm</p>

            <p>Saturaday - Sunday &nbsp;&nbsp; 11 am - 04 pm</p>
        </li>
        <div class="clear"></div>
    </ul>
</div>