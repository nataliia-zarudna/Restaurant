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
  <title>Orders [Admin]</title>
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
  <script src="js/popup.js"></script>
  <script>
    $(document).ready(function() {
      initGroupOrdersView("all");
    });
  </script>
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
        <input type="radio" id="usersModeRadio" name="radio"/>
        <label for="usersModeRadio">View by users</label>

        <input type="radio" id="dishesModeRadio" name="radio" checked="checked"/>
        <label for="dishesModeRadio">View by dishes</label>
      </div>

      <div id="groupOrdersView">

      </div>


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
