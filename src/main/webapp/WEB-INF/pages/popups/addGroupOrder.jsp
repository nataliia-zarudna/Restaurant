<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script>
    setShowPopupHandler(".addGroupOrder", "#addGroupOrderPopup");
</script>

<script>

</script>

<div class="box" id="addGroupOrderPopup" style="position:absolute; display: none;">
    <div class="containerWrapper">
        <div class="containerRegister tabContainer active">

            <jsp:useBean id="order" class="sirobaba.testtask.restaurant.model.entity.Order" scope="request"/>

            <form:form commandName="order" action="addGroupOrder" method="post">
                <h2 class="loginTitle">Add Group Order</h2>

                <div class="registerContent">
                    <form:errors path="title" cssClass="error_message" />
                    <div class="inputWrapper">
                        <form:input path="title" placeholder="Title"/>
                    </div>
                    <form:errors path="groupID" cssClass="error_message" />
                    <div class="inputWrapper">

                        <form:select path="groupID">
                            <c:forEach var="group" items="${userGroups}">
                                <form:option value="${group.id}">${group.title}</form:option>
                            </c:forEach>
                        </form:select>

                    </div>
                </div>
                <button class="greenBox" type="submit">
                    <span class="iconRegister"></span> Add
                </button>
                <div class="clear"></div>
            </form:form>
        </div>
        <div class="clear"></div>

    </div>
</div>