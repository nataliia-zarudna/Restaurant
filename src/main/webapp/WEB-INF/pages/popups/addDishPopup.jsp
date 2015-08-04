<script>
    setShowPopupHandler(".addDish_sectionID_${param.sectionID}", "#addDishPopup_sectionID_${param.sectionID}");
</script>

<div class="box" id="addDishPopup_sectionID_${param.sectionID}" style="position:absolute; display: none;">
    <div class="containerWrapper">
        <div class="containerRegister tabContainer active" id="addDishForm">

            <jsp:useBean id="dish" class="sirobaba.testtask.restaurant.model.entity.Dish" scope="request" />

            <!--form action="addDish" method="post"-->
            <h2 class="loginTitle">Add Dish</h2>

            <div class="registerContent">
                <div class="inputWrapper">
                    <input name="title" placeholder="Title" id="title_sectionID_${param.sectionID}"/>
                </div>
                <div class="inputWrapper">
                    <!--input name="icon" value="thumb-3.jpg" placeholder="Icon" /-->
                    <form method="POST" action="uploadFile" enctype="multipart/form-data" id="uploadFile_sectionID_${param.sectionID}">
                        File to upload: <input type="file" name="file" id="fileName_sectionID_${param.sectionID}"><br/>
                        Name: <input type="text" name="name"><br/> <br/>
                        <input type="submit" value="Upload" name="upload"> Press here to upload the file!
                    </form>
                </div>
                <div class="inputWrapper">
                    <input name="price" placeholder="Price" id="price_sectionID_${param.sectionID}"/>
                </div>
                <div class="inputWrapper">
                    <input name="description" placeholder="Description" id="description_sectionID_${param.sectionID}"/>
                </div>
            </div>
            <!--input name="sectionID" id="currentSectionID" value="${param.sectionID}" type="hidden"/-->
            <button class="greenBox" type="submit" id="createDishBtn_sectionID_${param.sectionID}">
                <!--span class="iconRegister"></span--> Create Dish
            </button>
            <div class="clear"></div>
            <!--/form-->
        </div>
        <div class="clear"></div>

    </div>
</div>