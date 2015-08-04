<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="top-head">
    <div class="welcome">Welcome To <span>Food Point</span></div>
    <div class="top-nav">
        <ul>
            <li>
                <sec:authorize var="isAuthenticated" access="isAuthenticated()"/>
                <c:choose>
                    <c:when test="${not isAuthenticated}">
                        <form action="/j_spring_security_check" method="post">
                            <input name="j_username"/>
                            <input name="j_password" type="password"/>
                            <input name="j_spring_security_remember_me" type="checkbox" checked/>
                            <input type="submit" value="Login"/>
                            <button type="button" id="register"> Register</button>
                        </form>

                        <%@include file="popups/registrationPopup.jsp" %>
                    </c:when>
                    <c:otherwise>
                        <a href="/logout" id="logout"> Logout</a>
                    </c:otherwise>
                </c:choose>
            </li>
        </ul>
    </div>
    <div class="clear"></div>



</div>
