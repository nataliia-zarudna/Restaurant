$(document).ready(init);

function init() {

    $(".dishesCount")
        .spinner({
            min: 0,
            spin: function (event, ui) {

                var oldValue = $(this)[0].value;
                var newValue = ui.value;

                var url = "";
                if (newValue > oldValue) {
                    url = "addDishToUserOrder";
                } else {
                    url = "removeDishFromUserOrder";
                }

                var orderID = $(this).attr("orderID");
                var dishID = $(this).attr("dishID");

                //var parentLI = $(this).closest("li");
                //var dishesPriceSpan = parentLI.find($(".dishesPrice[dishID=" + dishID + "]"));
                var dishesPriceSpan = $(".dishesPrice[dishID=" + dishID + "]");

                //var totalPriceSpan = parentLI.find($(".totalPrice"));
                var totalPriceSpan = $(".totalPrice");

                $.ajax({
                    url: url,
                    type: "get",
                    data: "orderID=" + orderID + "&dishID=" + dishID,
                    success: function (data) {

                        var dishesPrice = 0;
                        for(var i = 0; i < data.orderedDishes.length; i++) {

                            var currentOrderedDish = data.orderedDishes[i];
                            if(currentOrderedDish.dish.id == dishID) {
                                dishesPrice = currentOrderedDish.totalPrice;
                                break;
                            }
                        }
                        dishesPriceSpan.html(dishesPrice);

                        var totalPrice = data.totalPrice;
                        totalPriceSpan.html(totalPrice);
                    }
                });

                if(newValue == 0) {
                    var p = $(this).closest($("p"))[0];
                    if(p !== undefined) {
                        $(p).attr("hidden", "true");
                    }
                }
            }
        })
        .bind("keydown", function (event) {
            event.preventDefault();
        });

}
