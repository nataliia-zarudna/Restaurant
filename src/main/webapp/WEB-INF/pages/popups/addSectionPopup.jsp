<script>
    setShowPopupHandler(".addSection", "#addSectionPopup");
</script>

<div class="box" id="addSectionPopup" style="position:absolute; display: none;">
    <div class="containerWrapper">
        <div class="containerRegister tabContainer active">
            <form action="addSection" method="post">
                <h2 class="loginTitle">Add Section</h2>

                <div class="registerContent">
                    <div class="inputWrapper">
                        <input name="title" value="first dishes" placeholder="Title"/>
                    </div>
                    <div class="inputWrapper">
                        <input name="icon" value="first_dishes.png" placeholder="Icon"/>
                    </div>
                    <div class="inputWrapper">
                        <input name="description" value="best first dishes" placeholder="Description"/>
                    </div>
                </div>
                <button class="greenBox" type="submit">
                    <span class="iconRegister"></span> Create Section
                </button>
                <div class="clear"></div>
            </form>
        </div>
        <div class="clear"></div>

    </div>
</div>