<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE HTML>
<html>
<head>
    <title>Contact</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="all"/>
    <link rel="stylesheet" href="css/slider-styles.css" type="text/css" media="all"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href='http://fonts.googleapis.com/css?family=Libre+Baskerville' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

    <script src="js/lib/jquery.bpopup.min.js"></script>
    <script src="js/popup.js"></script>
    <link rel="stylesheet" href="css/popup.css" media="screen" type="text/css"/>
    <link rel='stylesheet prefetch' href='css/lib/font-awesome.min.css'>
</head>
<body>
<div class="wrap">

    <jsp:include page="header.jsp">
        <jsp:param name="activeMenuItem" value="contact"/>
    </jsp:include>

    <div class="main-body">

        <jsp:include page="imagesSlider.jsp"/>

        <div class="feed">
            <div class="feedback">
                <h1>Feedback</h1>

                <form>
                    <div>
                        <span><label>Name</label></span>
                        <span><input type="text" value=""/></span>
                    </div>
                    <div>
                        <span><label>Email</label></span>
                        <span><input type="text" value=""/></span>
                    </div>
                    <div>
                        <span><label>body</label></span>
                        <span><textarea></textarea></span>
                    </div>
                    <div>
                        <span><input type="submit" value="Submit"/></span>
                    </div>
                </form>
            </div>

            <jsp:include page="sideNav.jsp"/>

            <div class="clear"></div>
        </div>

        <div class="clear"></div>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>
