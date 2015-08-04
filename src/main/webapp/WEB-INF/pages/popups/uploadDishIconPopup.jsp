<script>
  setShowPopupHandler(".uploadDishIcon_dishID_${param.dishID}", "#uploadDishIcon_dishID_${param.dishID}");
</script>

<div class="box" id="uploadDishIcon_dishID_${param.dishID}" style="position:absolute; display: none;">
  <div class="containerWrapper">
    <div class="containerRegister tabContainer active" >

      <div class="registerContent">
        <div class="inputWrapper">
          <form method="POST" action="uploadIcon" enctype="multipart/form-data">
            <input type="file" name="file" ><br/>
            <input type="hidden" name="dishID" value="${param.dishID}" />
            <!--input type="text" name="name"><br/> <br/-->
            <input type="submit" value="Upload" name="upload" />
          </form>
        </div>
      </div>
      <!--input name="sectionID" id="currentSectionID" value="${param.sectionID}" type="hidden"/-->
      <div class="clear"></div>
    </div>
    <div class="clear"></div>

  </div>
</div>