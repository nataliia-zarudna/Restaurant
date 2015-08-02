/**
 * Created by Nataliia on 01.08.2015.
 */
$(document).ready(init);

function init() {

    $("#orderTitle, #datepicker, #timepicker").on("blur", function (e, data) {

        var orderID = $("#orderTitle").attr("orderID");
        var newTitle = $("#orderTitle").text().trim();

        var dateStr = $("#datepicker").datepicker({dateFormat: "MM/dd/yyyy"}).val();
        var timeStr = $("#timepicker").attr("value");

        $.ajax({
            url: 'updateOrder',
            type: 'POST',
            data: "id=" + orderID + "&title=" + newTitle
            + "&reservationTime=" + dateStr + " " + timeStr
            + "&reservationTimePattern=" + "MM/dd/yyyy hh:mm",
            success: function (data) {
                console.log("Section title has been updated");
            }
        });

    });

    /*$("#datepicker").datepicker({
     onSelect: function (dateText, inst) {
     console.log("dateText " + dateText);
     }
     });*/

}


