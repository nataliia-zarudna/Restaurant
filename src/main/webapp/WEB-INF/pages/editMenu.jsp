<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Edit Menu</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="all"/>
    <link rel="stylesheet" href="css/slider-styles.css" type="text/css" media="all"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
    <link href='http://fonts.googleapis.com/css?family=Libre+Baskerville' rel='stylesheet' type='text/css'>
    <script src="js/lib/jquery.bpopup.min.js"></script>
    <script src="js/popup.js"></script>
    <script src="js/editMenu.js"></script>
    <link rel="stylesheet" href="css/popup.css" media="screen" type="text/css"/>
</head>
<body>
<div class="wrap">

    <jsp:include page="header.jsp">
        <jsp:param name="activeMenuItem" value="editMenu"/>
    </jsp:include>

    <div class="main-body">

        <jsp:include page="imagesSlider.jsp"/>

        <div class="grids">

            <ul>
                <c:forEach var="sectionEntry" items="${menu}">

                    <h4 contenteditable="true" class="sectionTitle"
                        sectionID="${sectionEntry.key.id}">${sectionEntry.key.title}</h4>
                    <a href="/deleteSection?id=${sectionEntry.key.id}">
                        <img src="images/delete.png" title="Delete Section"/>
                    </a>


                    <!--a href="#" class="addDish">Add Dish</a-->

                    <!--a href="/deleteSection?id=${sectionEntry.key.id}">Delete Section</a-->

                    <c:forEach var="dish" items="${sectionEntry.value}">
                        <li dishID="${dish.id}" sectionID="${sectionEntry.key.id}">
                            <h3 contenteditable="true" dishEditableParam="title">${dish.title}</h3>
                            <a href="/deleteDish?id=${dish.id}">
                                <img src="images/delete.png" title="Delete Dish"/>
                            </a>

                            <img src="images/${dish.icon}"/>
                            <img src="images/edit_image.png" title="Edit Image"/>

                            <p contenteditable="true" dishEditableParam="description">${dish.description}</p>

                            <div class="price">$
                                <div contenteditable="true" dishEditableParam="price">${dish.price}</div>
                            </div>
                        </li>
                    </c:forEach>
                    <li>
                        <h3>
                            <a href="#" class="addDish_sectionID_${sectionEntry.key.id}">
                                <img src="images/add.png" title="Add Dish"/>
                            </a>
                        </h3>
                    </li>
                    <jsp:include page="popups/addDishPopup.jsp">
                        <jsp:param name="sectionID" value="${sectionEntry.key.id}"/>
                    </jsp:include>

                    <div class="clear"></div>
                </c:forEach>

                <h4><a href="#" class="addSection"><img src="images/add.png" title="Add Section"/></a></h4>
                <jsp:include page="popups/addSectionPopup.jsp"/>

            </ul>
            <div class="clear"></div>
        </div>

        <jsp:include page="sideNav.jsp"/>

        <div class="clear"></div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
