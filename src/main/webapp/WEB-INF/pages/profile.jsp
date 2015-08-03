<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <title>Menu</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="all"/>
    <link rel="stylesheet" href="css/slider-styles.css" type="text/css" media="all"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Libre+Baskerville' rel='stylesheet' type='text/css'>
</head>
<body>
<div class="wrap">

    <jsp:include page="header.jsp">
        <jsp:param name="activeMenuItem" value="profile"/>
    </jsp:include>

    <div class="main-body">

        <jsp:include page="imagesSlider.jsp"/>

        <div class="hr"></div>
        <div class="feed">
            <div class="feedback">

                <h1>Profile</h1>
                <form:form action="updateUser" method="post" commandName="user">
                    <div>
                        <span><label>First Name</label></span>
                        <span>
                            <form:input path="firstName" type="text" value="${user.firstName}"/>
                        </span>
                    </div>
                    <div>
                        <span><label>Last Name</label></span>
                        <span>
                            <form:input path="lastName" type="text" value="${user.lastName}"/>
                        </span>
                    </div>
                    <div>
                        <span><label>Password</label></span>
                        <span>
                            <form:input path="password" type="password" value="${user.password}"/>
                        </span>
                    </div>
                    <div>
                        <span><label>Phone</label></span>
                        <span>
                            <form:input path="phone" type="text" value="${user.phone}"/>
                        </span>
                    </div>
                    <div>
                        <span><label>E-mail</label></span>
                        <span>
                            <form:input path="email" type="text" value="${user.email}"/>
                        </span>
                    </div>
                    <form:input path="id" value="${user.id}" type="hidden" />
                    <div>
                        <span><input type="submit" value="Update"/></span>
                    </div>
                </form:form>
            </div>
            <div class="clear"></div>
        </div>

        <div class="clear"></div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<div class="clear">
</div>
</div>

</body>
</html>
