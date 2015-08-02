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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
    <script type="text/javascript" src="js/slider.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Libre+Baskerville' rel='stylesheet' type='text/css'>
    <script src="js/lib/jquery.bpopup.min.js"></script>
    <link rel="stylesheet" href="css/popup.css" media="screen" type="text/css"/>

    <script>
        $(function () {
            var availableGroups = [
                <c:forEach var="group" items="${allGroups}" >
                {
                    value: "${group.id}",
                    label: "${group.title}"
                },
                </c:forEach>
                {
                    value: "0",
                    label: "last"
                },
            ];
            $("#groups").autocomplete({
                source: availableGroups
            });
        });
    </script>
</head>
<body>
<div class="wrap">

    <jsp:include page="header.jsp">
        <jsp:param name="activeMenuItem" value="myGroups"/>
    </jsp:include>

    <div class="main-body">

        <jsp:include page="imagesSlider.jsp"/>

        <div class="lists">

            <!--div class="ui-widget">
                <label for="groups">Groups: </label>
                <input id="groups">
            </div-->

            <select id="availableGroups">
                <c:forEach var="group" items="${allGroups}">
                    <option value="${group.id}">${group.title}</option>
                </c:forEach>
            </select>
            <a onclick="javascript: var groupID = $('#availableGroups option:selected').attr('value');
            location.href='/joinGroup?id=' + groupID">Join Group</a>

            <br/><br/>

            <ul>

                <c:forEach var="group" items="${userGroups}">

                    <h4>${group.group.title}
                        <a href="/deleteGroup?id=${group.group.id}">
                            <img src="images/delete.png" title="Delete Group"/>
                        </a>
                        <a href="/leaveGroup?groupID=${group.group.id}">
                            <img src="images/leave.png" title="Leave Group"/>
                        </a>
                    </h4>

                    <c:forEach var="user" items="${group.users}">
                        <li>
                            <h3>${user.firstName} ${user.lastName}</h3>

                            <!--p>${user.firstName} ${user.lastName}</p-->
                            <!--button onclick="location.href='orderDish?dishID=${dish.id}'">Order</button-->
                        </li>
                    </c:forEach>
                    <c:forEach var="userRequested" items="${group.userRequests}">
                        <li>
                            <!--h3>${user.firstName} ${user.lastName}</h3-->

                            <p>${userRequested.firstName} ${userRequested.lastName}
                                <a href="declineRequest?groupID=${group.group.id}&userID=${userRequested.id}"
                                   class="group_btn">
                                    <img src="images/decline.png" title="Decline"/>
                                </a>
                                <a href="acceptRequest?groupID=${group.group.id}&userID=${userRequested.id}"
                                   class="group_btn">
                                    <img src="images/accept.png" title="Accept"/>
                                </a>
                            </p>
                            <!--button onclick="location.href='acceptRequest?groupID=${group.group.id}&userID=${userRequested.id}'">
                                Accept
                            </button>
                            <button onclick="location.href='declineRequest?groupID=${group.group.id}&userID=${userRequested.id}'">
                                Decline
                            </button-->
                        </li>
                    </c:forEach>

                    <div class="clear"></div>
                </c:forEach>

                <a href="#" class="addGroup">Add Group</a>
                <br/><br/>

                <h4>Your requests</h4>
                <c:choose>
                    <c:when test="${not empty requestedGroups}">
                        <c:forEach var="group" items="${requestedGroups}">
                            <li>
                                <h3>${group.title}
                                    <a href="removeRequest?groupID=${group.id}" class="group_btn">
                                        <img src="images/delete.png" title="Delete Request"/>
                                    </a>
                                </h3>
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <p>No requests</p>
                    </c:otherwise>
                </c:choose>

            </ul>
            <div class="clear"></div>
        </div>

        <jsp:include page="sideNav.jsp"/>

        <div class="clear"></div>
    </div>
</div>


<jsp:include page="footer.jsp"/>

<jsp:include page="popups/addGroupPopup.jsp"/>

</body>
</html>
