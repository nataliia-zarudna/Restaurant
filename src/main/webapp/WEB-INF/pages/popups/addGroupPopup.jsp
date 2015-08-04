<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script>
  setShowPopupHandler(".addGroup", "#addGroupPopup");

  if ('${createError}' == 'true') {
    $(document).ready(function (e, data) {
      showPopup("#addGroupPopup");
    });
  }
</script>

<div class="box" id="addGroupPopup" style="position:absolute; display: none;">
  <div class="containerWrapper">
    <div class="containerRegister tabContainer active">

      <jsp:useBean id="group" class="sirobaba.testtask.restaurant.model.entity.Group" scope="request" />

      <form:form commandName="group" action="addGroup" method="post">
        <h2 class="loginTitle">Add Group</h2>
        <div class="registerContent">
          <form:errors path="title" cssClass="error_message"/>
          <div class="inputWrapper">
            <form:input path="title" value="Colleagues" placeholder="Title" />
          </div>
        </div>

        <sec:authentication var="user" property="principal" scope="request" />
        <form:input path="ownerID" value="${user.id}" type="hidden"/>
        <button class="greenBox" type="submit">
          <span class="iconRegister"></span> Create Group
        </button>
        <div class="clear"></div>
      </form:form>
    </div>
    <div class="clear"></div>

  </div>
</div>