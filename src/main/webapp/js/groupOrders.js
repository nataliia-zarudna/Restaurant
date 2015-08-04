/**
 * Created by Nataliia on 02.08.2015.
 */
function initGroupOrdersView(mode) {

    $("#datepicker").datepicker();

    $("#radio").buttonset();

    prevOrderDetails = null;

    $("#usersModeRadio").on("change", function(e, data) {

        updateOrdersInfo("GroupOrderDetailsByUsers", mode);
        prevMode = mode;
    });
    $("#dishesModeRadio").on("change", function(e, data) {

        updateOrdersInfo("GroupOrderDetailsByDishes", mode);
        prevMode = mode;
    });

    (function () {
        setInterval(function () {
            console.log("mode " + mode);
            updateOrdersInfoByMode(mode);
        }, 3000);
    })();
}

function updateOrdersInfoByMode(mode) {
    console.log("updateOrdersInfo without params");

    var url;
    if ($('#usersModeRadio').is(':checked')) {
        url = "GroupOrderDetailsByUsers";
    } else {
        url = "GroupOrderDetailsByDishes";
    }
    updateOrdersInfo(url, mode);

}

function updateOrdersInfo(url, mode) {

    var url = mode + url;

    /*if(event.data !== undefined) {
        url = event.data.url;
    } else {
        url = event;
    }*/
    console.log("updateOrdersInfo: url " + url);

    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (orderDetails) {
            console.log("updateOrdersInfo SUCCESS");
            console.log(orderDetails);
            showOrderDetailses(orderDetails, mode);
        }
    });
}

function showOrderDetailses(orderDetails, mode) {

    if(prevOrderDetails === orderDetails && prevMode === mode) {
        return;
    }

    console.log("showOrderDetailses: orderDetails " + orderDetails);

    var groupOrdersDiv = $("#groupOrdersView");
    var ordersHTML = "";

    if (orderDetails === undefined) {
        ordersHTML = '<h4>No Orders</h4>';

    } else {

        ordersHTML += '<ul>';
        for (var k = 0; k < orderDetails.length; k++) {

            var orderDetail = orderDetails[k];

            ordersHTML += '<h4>' +
                '<a href="order?id=' + orderDetail.order.id + '"' +
                'class="orderTitle">' + orderDetail.order.title +
                '</a>' +
                '<a href="/cancelOrder?id=' + orderDetail.order.id + '">' +
                '<img src="images/cancel_order.png" title="Cancel Order"/>' +
                '</a>' +
                '</h4>';

            if ($('#usersModeRadio').is(':checked')) {
                ordersHTML += getUsersViewDiv(orderDetail);
            } else {
                ordersHTML += getDishesViewDiv(orderDetail);
            }

            ordersHTML += '<p>Total &nbsp;&nbsp;<span>' + orderDetail.totalPrice + '</span></p>' +
                '<p>Status &nbsp;&nbsp;<span>' + orderDetail.orderStatus + '</span></p>' +
                '<a href="/startOrdering?orderID=' + orderDetail.order.id + '">Add Dishes</a>' +
                '<div class="clear"></div>';
        }
        ordersHTML += '</ul>';
    }

    groupOrdersDiv.html(ordersHTML);

    prevOrderDetails = orderDetails;
}


function getUsersViewDiv(groupOrderDetails) {

    var divHTML = '<div class="usersMode">';

    console.log(groupOrderDetails.usersOrderedDetails);
    for (var user in groupOrderDetails.usersOrderedDetails) {

        var userOrderDetails = groupOrderDetails.usersOrderedDetails[user];

        //user = user.substring(4, user.length).replace(/=/g, ":");
        console.log(user);
        user = jQuery.parseJSON(user);
        console.log(user);
        console.log(userOrderDetails);

        divHTML += '<h3>' + user.firstName + ' ' + user.lastName + '</h3>' +
            '<table style="width: 100%">';

        var totalPrice = 0;
        if (userOrderDetails.orderedDishes !== undefined) {
            for (var j = 0; j < userOrderDetails.orderedDishes.length; j++) {

                var orderedDish = userOrderDetails.orderedDishes[j];
                divHTML += '<tr>' +
                    '<td><p>' + orderedDish.dish.title + '</p></td>' +
                    '<td><p>' + orderedDish.count + '</p></td>' +
                    '<td><p>$' + orderedDish.totalPrice + '</p></td>' +
                    '</tr>';
            }
            totalPrice = userOrderDetails.totalPrice;
        }

        divHTML += '</table>' +
            '<p>Total &nbsp;&nbsp;<span>' + totalPrice + '</span></p>' +
            '<hr/>';
    }
    divHTML += '</div>';

    return divHTML;
}

function getDishesViewDiv(orderDetails) {

    var divHTML = '<div class="dishesMode">' +
        '<table style="width: 100%">';

    for (var i = 0; i < orderDetails.orderedDishes.length; i++) {

        var orderedDish = orderDetails.orderedDishes[i];
        divHTML += '<tr>' +
            '<td><p>' + orderedDish.dish.title + '</p></td>' +
            '<td><p>' + orderedDish.count + '</p></td>' +
            '<td><p>$' + orderedDish.totalPrice + '</p></td>' +
            '</tr>';
    }
    divHTML += '</table>' +
        '<hr/>' +
        '</div>';
    return divHTML;
}
