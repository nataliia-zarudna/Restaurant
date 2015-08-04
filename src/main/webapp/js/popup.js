function setShowPopupHandler(handler, popup) {

    $(handler).on("click", function (e, data) {

        console.log("showPopup: " + handler + " " + popup);

        showPopup($(popup));
    });
}

function showPopup(popup) {
    $(popup).bPopup({
        position: ['auto', 'auto']
    });
}