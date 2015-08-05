<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="nav">
    <ul>
        <li id="home">
            <a href="/">Home</a>
        </li>
        <li id="menu">
            <a href="/menu">Menu</a></a>
        </li>

        <sec:authorize access="isAuthenticated()">

            <sec:authentication var="role" property="principal.authorities"/>

            <c:if test="${role eq '[ROLE_USER]'}">
                <li id="myOrders">
                    <a href="/myOrders">My orders</a>
                </li>
                <li id="groupOrders">
                    <a href="/groupOrders">Group orders</a>
                </li>
                <li id="myGroups">
                    <a href="/groups">My Groups | ${requestsCount} req</a>
                </li>
                <li id="profile">
                    <a href="/profile">Profile</a>
                </li>
            </c:if>

            <c:if test="${role eq '[ROLE_ADMIN]'}">
                <li id="editMenu">
                    <a href="/editMenu">Edit Menu</a>
                </li>
                <li id="usersAdmin">
                    <a href="/usersAdmin">Users</a>
                </li>
                <li id="groupsAdmin">
                    <a href="/groupsAdmin">Groups</a>
                </li>
                <li>
                    <a href="/ordersAdmin">Orders</a>
                </li>
                <li id="orders">
                    <a href="/groupOrdersAdmin">Group Orders</a>
                </li>
            </c:if>

        </sec:authorize>

        <li id="contact">
            <a href="contact">contact</a>
        </li>
        <div class="clear"></div>
    </ul>
</div>

<script>
    $('#' + '${param.activeMenuItem}').attr('class', 'active');
</script>