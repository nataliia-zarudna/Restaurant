<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="js/lib/jquery.bpopup.min.js"></script>
<script src="js/popup.js"></script>
<link rel="stylesheet" href="css/popup.css" media="screen" type="text/css"/>
<link rel='stylesheet prefetch' href='css/lib/font-awesome.min.css'>
<script>
    if('${createError}' == 'true') {
        $(document).ready(function(e, data){
            showPopup($("#autorize-popup"));
        });
    }
</script>

<div class="box" id="autorize-popup" style="position:absolute; display: none;">

    <div class="containerWrapper">

        <div class="containerRegister tabContainer active">

            <jsp:useBean id="user" class="sirobaba.testtask.restaurant.model.entity.User" scope="request" />

            <form:form  commandName="user" method="post" action="register">
                <h2 class="loginTitle">Registration</h2>

                <div class="registerContent">
                    <form:errors path="firstName" cssClass="error_message" />
                    <div class="inputWrapper">
                        <form:input type="text" path="firstName" placeholder="First Name"/>
                    </div>
                    <div class="inputWrapper">
                        <form:input type="text" path="lastName" placeholder="Last Name"/>
                    </div>
                    <div class="inputWrapper">
                        <form:input type="text" path="phone" placeholder="Phone Number"/>
                    </div>
                    <form:errors path="email" cssClass="error_message" />
                    <div class="inputWrapper">
                        <form:input type="email" path="email" placeholder="E-mail"/>
                    </div>
                    <form:errors path="password" cssClass="error_message" />
                    <div class="inputWrapper">
                        <form:input type="password" path="password" placeholder="Password"/>
                    </div>
                </div>
                <input type="checkbox" checked="checked" hidden="hidden" />
                <input type="hidden" name="returnURL" value="${pageContext.request.requestURI}"/>
                <button class="greenBox" type="submit">
                    <span class="iconRegister"></span> Register
                </button>
                <div class="clear"></div>
            </form:form>
        </div>

        <div class="clear"></div>

    </div>
</div>

