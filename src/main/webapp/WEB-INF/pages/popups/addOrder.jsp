<script>
    setShowPopupHandler(".addOrder", "#addOrderPopup");
</script>
<script>
    setShowPopupHandler("#datepicker", "#ui-datepicker-div");
</script>

<div class="box" id="addOrderPopup" style="position:absolute; display: none;">
    <div class="containerWrapper">
        <div class="containerRegister tabContainer active">
            <form action="addUserOrder" method="post">
                <h2 class="loginTitle">Add Order</h2>

                <div class="registerContent">
                    <div class="inputWrapper">
                        <input name="title" value="Birthday Party" placeholder="Title"/>
                    </div>
                </div>
                <button class="greenBox" type="submit">
                    <span class="iconRegister"></span> Create Order
                </button>
                <div class="clear"></div>
            </form>
        </div>
        <div class="clear"></div>

    </div>
</div>