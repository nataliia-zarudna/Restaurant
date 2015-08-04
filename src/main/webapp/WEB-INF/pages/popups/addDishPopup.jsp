<script>
    setShowPopupHandler(".addDish_sectionID_${param.sectionID}", "#addDishPopup_sectionID_${param.sectionID}");
</script>

<div class="box" id="addDishPopup_sectionID_${param.sectionID}" style="position:absolute; display: none;">
    <div class="containerWrapper">
        <div class="containerRegister tabContainer active" id="addDishForm">
            <!--form action="addDish" method="post"-->
            <h2 class="loginTitle">Add Dish</h2>

            <div class="registerContent">
                <div class="inputWrapper">
                    <input name="title" value="soup" placeholder="Title"/>
                </div>
                <div class="inputWrapper">
                    <!--input name="icon" value="thumb-3.jpg" placeholder="Icon" /-->
                    <form method="POST" action="uploadFile" enctype="multipart/form-data">
                        File to upload: <input type="file" name="file"><br/>
                        Name: <input type="text" name="name"><br/> <br/>
                        <input type="submit" value="Upload"> Press here to upload the file!
                    </form>
                </div>
                <div class="inputWrapper">
                    <input name="price" value="40" placeholder="Price"/>
                </div>
                <div class="inputWrapper">
                    <input name="description" value="best soup" placeholder="Description"/>
                </div>
            </div>
            <input name="sectionID" id="currentSectionID" value="${param.sectionID}" type="hidden"/>
            <button class="greenBox" type="submit" id="createDishBtn">
                <span class="iconRegister"></span> Create Dish
            </button>
            <div class="clear"></div>
            <!--/form-->
        </div>
        <div class="clear"></div>

    </div>
</div>