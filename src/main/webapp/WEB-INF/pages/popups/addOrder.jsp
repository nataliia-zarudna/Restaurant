<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script>
    setShowPopupHandler(".addOrder", "#addOrderPopup");
</script>
<script>
    setShowPopupHandler("#datepicker", "#ui-datepicker-div");
</script>

<div class="box" id="addOrderPopup" style="position:absolute; display: none;">
    <div class="containerWrapper">
        <div class="containerRegister tabContainer active">

            <jsp:useBean id="order" class="sirobaba.testtask.restaurant.model.entity.Order" scope="request"/>

            <form:form commandName="order" action="addUserOrder" method="post">
                <h2 class="loginTitle">Add Order</h2>

                <div class="registerContent">
                    <form:errors path="title" cssClass="error_message" />
                    <div class="inputWrapper">
                        <form:input path="title" placeholder="Title"/>
                    </div>
                </div>
                <button class="greenBox" type="submit">
                    <span class="iconRegister"></span> Create Order
                </button>
                <div class="clear"></div>
            </form:form>
        </div>
        <div class="clear"></div>

    </div>
</div>