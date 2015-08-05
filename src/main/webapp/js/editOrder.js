/**
 * Created by Nataliia on 01.08.2015.
 */
$(document).ready(init);

function init() {

    $("#orderTitle, #timepicker").on("blur", function (e, data) {
        updateOrder();
    });

    $("#datepicker").datepicker({
        onSelect: function(text) {
            $(this).change();
            updateOrder();
        }
    });

    function updateOrder() {

        var orderID = $("#orderTitle").attr("orderID");
        var newTitle = $("#orderTitle").text().trim();

        var dateStr = $("#datepicker").datepicker({dateFormat: "MM/dd/yyyy"}).val();
        var timeStr = $("#timepicker").val();

        $.ajax({
            url: 'updateOrder',
            type: 'POST',
            data: "id=" + orderID + "&title=" + newTitle
            + "&reservationTime=" + dateStr + "_" + timeStr
            + "&reservationTimePattern=" + "MM/dd/yyyy_HH:mm",
            success: function (data) {
                console.log("Section title has been updated");
            }
        });

    }

}


