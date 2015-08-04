$(document).ready(init);

function init() {

    setShowPopupHandler("#register", "#autorize-popup");

    // showPopup(".addDish", "#addDishPopup");

    //showPopup(".addSection", "#addSectionPopup");

    setShowPopupHandler(".addGroup", "#addGroupPopup");

    setShowPopupHandler(".addOrder", "#addOrderPopup");

    setShowPopupHandler(".addGroupOrder", "#addGroupOrderPopup");

}

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