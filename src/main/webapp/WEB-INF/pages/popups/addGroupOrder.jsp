<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script>
    setShowPopupHandler(".addGroupOrder", "#addGroupOrderPopup");
</script>
<!--link rel="styleshee" type="text/css" href="css/lib/jquery.timepicker.css" />
<link rel="stylesheet" type="text/css" href="css/lib/bootstrap-datepicker.css" />
<script type="text/javascript" src="js/lib/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="js/lib/jquery.timepicker.js"></script>

<script type="text/javascript" src="js/lib/datepair.js"></script>
<script>
// initialize input widgets first
$('#basicExample .time').timepicker({
'showDuration': true,
'timeFormat': 'g:ia'
});

$('#basicExample .date').datepicker({
'format': 'm/d/yyyy',
'autoclose': true
});

// initialize datepair
var basicExampleEl = document.getElementById('basicExample');
var datepair = new Datepair(basicExampleEl);
</script-->

<script>

</script>

<div class="box" id="addGroupOrderPopup" style="position:absolute; display: none;">
    <div class="containerWrapper">
        <div class="containerRegister tabContainer active">

            <jsp:useBean id="order" class="sirobaba.testtask.restaurant.model.entity.Order" scope="request"/>

            <form:form commandName="order" action="addGroupOrder" method="post">
                <h2 class="loginTitle">Add Group Order</h2>

                <div class="registerContent">
                    <div class="inputWrapper">
                        <form:input path="title" value="Birthday Party" placeholder="Title"/>
                    </div>
                    <div class="inputWrapper">

                        <form:select path="groupID">
                            <c:forEach var="group" items="${userGroups}">
                                <form:option value="${group.id}">${group.title}</form:option>
                            </c:forEach>
                        </form:select>

                    </div>
                    <div class="inputWrapper">
                        <form:input path="reservationTime" id="datepicker" type="hidden"/>
                    </div>
                    <div class="inputWrapper">
                        <input type="text" class="date start"/>

                    </div>
                    <div class="inputWrapper">
                        <input type="text" class="time start"/>
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