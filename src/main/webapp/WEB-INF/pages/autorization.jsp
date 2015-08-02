<div class="top-head">
  <div class="welcome">Welcome To <span>Food Point</span></div>
  <div class="top-nav">
    <ul>
      <li>
        <form action="/j_spring_security_check" method="post">
          <input name="j_username"/>
          <input name="j_password" type="password"/>
          <input name="j_spring_security_remember_me" type="checkbox" checked/>
          <input type="submit" value="Login"/>
        </form>
      </li>
      <li><a href="#" id="register"> Register</a></li>
    </ul>
  </div>
  <div class="clear"></div>

  <%@include file="popups/registrationPopup.jsp" %>

</div>
