<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script>
    setShowPopupHandler(".addSection", "#addSectionPopup");

    if ('${createError}' == 'true') {
        $(document).ready(function (e, data) {
            showPopup("#addSectionPopup");
        });
    }
</script>

<div class="box" id="addSectionPopup" style="position:absolute; display: none;">
    <div class="containerWrapper">
        <div class="containerRegister tabContainer active">

            <jsp:useBean id="section" class="sirobaba.testtask.restaurant.model.section.Section" scope="request"/>

            <form:form commandName="section" action="addSection" method="post">
                <h2 class="loginTitle">Add Section</h2>

                <div class="registerContent">
                    <form:errors path="title" cssClass="error_message"/>
                    <div class="inputWrapper">
                        <form:input path="title" value="first dishes" placeholder="Title"/>
                    </div>
                </div>
                <button class="greenBox" type="submit">
                    <span class="iconRegister"></span> Create Section
                </button>
                <div class="clear"></div>
            </form:form>
        </div>
        <div class="clear"></div>

    </div>
</div>