<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="js/lib/jquery.bpopup.min.js"></script>
<script src="js/popup.js"></script>
<link rel="stylesheet" href="css/popup.css" media="screen" type="text/css"/>
<link rel='stylesheet prefetch' href='css/lib/font-awesome.min.css'>


<div class="box" id="autorize-popup" style="position:absolute; display: none;">

    <div class="containerWrapper">

        <!--div class="containerRegister tabContainer active">
            <form  method="post" action="register">
                <h2 class="loginTitle">Registration</h2>

                <div class="registerContent">
                    <div class="inputWrapper">
                        <input type="text" name="firstName" placeholder="First Name"/>
                    </div>
                    <div class="inputWrapper">
                        <input type="text" name="lastName" placeholder="Last Name"/>
                    </div>
                    <div class="inputWrapper">
                        <input type="text" name="phone" placeholder="Phone Number"/>
                    </div>
                    <div class="inputWrapper">
                        <input type="email" name="email" placeholder="E-mail"/>
                    </div>
                    <div class="inputWrapper">
                        <input type="password" name="password" placeholder="Password"/>
                    </div>
                    <div class="inputWrapper">
                        <input type="password" placeholder="Confirm Password"/>
                    </div>
                </div>
                <input type="hidden" name="returnURL" value="${pageContext.request.servletPath}"/>
                <button class="greenBox" type="submit">
                    <span class="iconRegister"></span> Register
                </button>
                <div class="clear"></div>
            </form>
        </div-->

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

