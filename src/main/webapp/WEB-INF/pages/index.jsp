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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Libre+Baskerville' rel='stylesheet' type='text/css'>



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

        <%

            // you can get an enumeratable list
            // of parameter keys by using request.getParameterNames()
            Enumeration en = request.getParameterNames();

            // enumerate through the keys and extract the values
            // from the keys!
            while (en.hasMoreElements()) {
                String parameterName = (String) en.nextElement();
                String parameterValue = request.getParameter(parameterName);
                out.println(parameterName+":"+parameterValue+"<br />");
            }

            // now call your jsp file (from a browser and add on some paramters)
            // file.jsp?a=12341234&b=apple&c=1.21gigawatts

        %>

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



<div class="box" id="autorize-popup" style="position:absolute; display: none;">

    <div class="containerWrapper">

        <div class="containerRegister tabContainer active">
            <form:form  commandName="user" method="post" action="register">
                <h2 class="loginTitle">Registration</h2>

                <div class="registerContent">
                    <div class="inputWrapper">
                        <form:input type="text" path="firstName" placeholder="First Name"/>
                        <form:errors path="firstName" />
                    </div>
                    <div class="inputWrapper">
                        <form:input type="text" path="lastName" placeholder="Last Name"/>
                        <form:errors path="lastName" />
                    </div>
                    <div class="inputWrapper">
                        <form:input type="text" path="phone" placeholder="Phone Number"/>
                    </div>
                    <div class="inputWrapper">
                        <form:input type="email" path="email" placeholder="E-mail"/>
                    </div>
                    <div class="inputWrapper">
                        <form:input type="password" path="password" placeholder="Password"/>
                    </div>
                    <div class="inputWrapper">
                        <input type="password" placeholder="Confirm Password"/>
                    </div>
                </div>
                <input type="checkbox" checked="checked" hidden="hidden" />
                <input type="hidden" name="returnURL" value="${pageContext.request.servletPath}"/>
                <button class="greenBox" type="submit">
                    <span class="iconRegister"></span> Register
                </button>
                <div class="clear"></div>
            </form:form>
        </div>

        <div class="clear"></div>

    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
