/**
 * Created by Nataliia on 02.08.2015.
 */
$(document).ready(init);

function init() {

    $("#datepicker").datepicker();


    $("#radio").buttonset();
    $('.dishesMode').hide();

    function updateOrdersInfo() {
        console.log("updateOrdersInfo");

        $.ajax({
            url: "groupOrderDetails",
            type: "GET",
            dataType: "json",
            success: function (data) {
                console.log("updateOrdersInfo");
                console.log(data);
            }
        });
    }

    setTimeout(updateOrdersInfo, 30);


}
