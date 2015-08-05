<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="js/orderCount.js"></script>
<div class="boxes">
    <div class="order">
        <ul>
            <li>

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
                                        &nbsp;&nbsp;
                                        <span>
                                            <input class="dishesCount"
                                                   value="${orderedDish.count}"
                                                   dishID="${orderedDish.dish.id}"
                                                   orderID="${currentOrderDetails.order.id}"/>
                                        </span>
                                        &nbsp;&nbsp;<span class="dishesPrice"
                                                          dishID="${orderedDish.dish.id}">
                                                          ${orderedDish.totalPrice}
                                        </span></p>

                                </c:forEach>

                                <p>Total &nbsp;&nbsp;<span class="totalPrice"
                                                           orderID="${currentOrderDetails.order.id}">${currentOrderDetails.totalPrice}</span>
                                </p>

                                <h6>
                                    <a href="/cancelOrder?id=${currentOrderDetails.order.id}" class="orderBtn">Cancel
                                        Order</a>
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
</div>