<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    showPopup(".addGroupOrder", "#addGroupOrderPopup");
</script>

<div class="box" id="addGroupOrderPopup" style="position:absolute; display: none;">
  <div class="containerWrapper">
    <div class="containerRegister tabContainer active">
      <form action="addGroupOrder" method="post">
        <h2 class="loginTitle">Add Group Order</h2>

        <div class="registerContent">
          <div class="inputWrapper">
            <input name="title" value="Birthday Party" placeholder="Title"/>
          </div>
          <div class="inputWrapper">

            <select name="groupID">
              <c:forEach var="group" items="${userGroups}">
                <option value="${group.id}">${group.title}</option>
              </c:forEach>
            </select>

          </div>
          <div class="inputWrapper">
            <input name="reservationTime" id="datepicker" type="datetime"/>
          </div>
        </div>
        <button class="greenBox" type="submit">
          <span class="iconRegister"></span> Add
        </button>
        <div class="clear"></div>
      </form>
    </div>
    <div class="clear"></div>

  </div>
</div>