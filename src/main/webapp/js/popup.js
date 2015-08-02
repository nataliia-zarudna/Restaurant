$(document).ready(init);

function init() {

    showPopup("#register", "#autorize-popup");

   // showPopup(".addDish", "#addDishPopup");

    //showPopup(".addSection", "#addSectionPopup");

    showPopup(".addGroup", "#addGroupPopup");

    showPopup(".addOrder", "#addOrderPopup");

    showPopup(".addGroupOrder", "#addGroupOrderPopup");

}

function showPopup(handler, popup) {

    $(handler).on("click", function (e, data) {

        console.log("showPopup: " + handler + " " + popup);

        $(popup).bPopup({
            position: ['auto', 'auto']
        });
    });
}