/**
 * Created by Nataliia on 02.08.2015.
 */
function initGroupOrderView(orderID, userID) {

    currentUserID = userID;

    $("#datepicker").datepicker();

    $("#radio").buttonset();

    prevOrderDetails = null;

    $("#usersModeRadio").on("change", function (e, data) {
        updateOrdersInfoByURL("groupOrderDetailsByUsers", orderID);
    });
    $("#dishesModeRadio").on("change", function (e, data) {
        updateOrdersInfoByURL("groupOrderDetailsByDishes", orderID);
    });

    updateOrdersInfo(orderID);
    (function () {
        setInterval(function () {
            updateOrdersInfo(orderID);
        }, 1000);
    })();
}
function updateOrdersInfo(orderID) {
    console.log("updateOrdersInfo without params");

    var url;
    if ($('#usersModeRadio').is(':checked')) {
        url = "groupOrderDetailsByUsers";
    } else {
        url = "groupOrderDetailsByDishes";
    }
    updateOrdersInfoByURL(url, orderID);

}

function updateOrdersInfoByURL(url, orderID) {

    console.log("updateOrdersInfo: url " + url);

    $.ajax({
        url: url,
        type: "GET",
        data: "orderID=" + orderID,
        dataType: "json",
        success: function (orderDetails) {
            console.log("updateOrdersInfo SUCCESS");
            console.log(orderDetails);
            showOrderDetailses(orderDetails);
        }
    });
}

function showOrderDetailses(orderDetail) {

    if (prevOrderDetails === orderDetail) {
        return;
    }

    console.log("showOrderDetailses: orderDetail " + orderDetail);

    $("#orderTitle").html(orderDetail.order.title);

    if ($('#usersModeRadio').is(':checked')) {
        fillUsersViewDiv(orderDetail);
    } else {
        fillDishesViewDiv(orderDetail);
    }

    $(".totalPrice").html(orderDetail.order.totalPrice);
    $(".status").html(orderDetail.order.status);

    setReservationTime(orderDetail.order.reservationTime);

    initDishesCountIncreaser();

    prevOrderDetails = orderDetail;
}


function fillUsersViewDiv(groupOrderDetails) {

    $("#dishesView").attr("hidden", "true");
    $("#usersView").removeAttr("hidden");

    var divHTML = "";

    console.log(groupOrderDetails.usersOrderedDetails);
    for (var user in groupOrderDetails.usersOrderedDetails) {

        var userOrderDetails = groupOrderDetails.usersOrderedDetails[user];

        user = jQuery.parseJSON(user);

        divHTML += '<h3>' + user.firstName + ' ' + user.lastName + '</h3>' +
            '<table style="width: 100%">';

        var totalPrice = 0;
        if (userOrderDetails.orderedDishes !== undefined) {
            for (var j = 0; j < userOrderDetails.orderedDishes.length; j++) {

                var orderedDish = userOrderDetails.orderedDishes[j];
                if(user.id == currentUserID) {
                    divHTML += getEditedDishesRow(orderedDish, userOrderDetails);
                }else {
                    divHTML += getNonEditedDishesRow(orderedDish);
                }
            }
            totalPrice = userOrderDetails.totalPrice;
        }

        divHTML += '</table>' +
            '<p>Total &nbsp;&nbsp;<span>' + totalPrice + '</span></p>';
    }

    $("#usersView").html(divHTML);
}

function fillDishesViewDiv(orderDetails) {

    $("#dishesView").attr("hidden", "true");
    $("#usersView").removeAttr("hidden");

    var divHTML = '<table width="100%">';

    for (var i = 0; i < orderDetails.orderedDishes.length; i++) {

        var orderedDish = orderDetails.orderedDishes[i];
        divHTML += getNonEditedDishesRow(orderedDish);
    }
    divHTML += '</table>';
    $("#usersView").html(divHTML);
}

function getEditedDishesRow(orderedDish, orderDetails) {
    return '<tr>' +
        '<td><p>' + orderedDish.dish.title + '</p></td>' +
        '<td><p><input class="dishesCount"' +
        'value="' + orderedDish.count + '"' +
        'dishID="' + orderedDish.dish.id + '"' +
        'orderID="' + orderDetails.order.id + '"/></p></td>' +
        '<td><p class="dishesPrice"' +
        'dishID="' + orderedDish.dish.id + '">$' + orderedDish.totalPrice + '</p></td>' +
        '</tr>';
}

function getNonEditedDishesRow(orderedDish) {
    return '<tr>' +
        '<td><p>' + orderedDish.dish.title + '</p></td>' +
        '<td><p>' + orderedDish.count + '</p></td>' +
        '<td><p>$' + orderedDish.totalPrice + '</p></td>' +
        '</tr>';
}

function setReservationTime(reservationTime) {

    var reservationDate = new Date(reservationTime);
    $("#datepicker").datepicker("setDate", reservationDate);

    var hours = reservationDate.getHours();
    var formattedHours = (hours < 10) ? ("0" + hours) : hours;
    $("#timepicker").val(formattedHours + ":" + reservationDate.getMinutes());

}