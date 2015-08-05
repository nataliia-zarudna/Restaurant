/**
 * Created by sirobaban on 04.08.2015.
 */
$(document).ready(init);

function init() {

    $(".order_btn")
        .button()
        .next()
        .button({
            text: false,
            icons: {
                primary: "ui-icon-triangle-1-s"
            }
        })
        .click(function () {
            var menu = $(this).parent().next().show().position({
                my: "left top",
                at: "left bottom",
                of: this
            });
            $(document).one("click", function () {
                menu.hide();
            });
            return false;
        })
        .parent()
        .buttonset()
        .next()
        .hide()
        .menu();

}
