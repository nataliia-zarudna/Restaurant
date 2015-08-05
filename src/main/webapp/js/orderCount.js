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

                var parentLI = $(this).closest("li");
                var dishesPriceSpan = parentLI.find($(".dishesPrice[dishID=" + dishID + "]"));
                console.log("dishesPriceSpan");
                console.log(dishesPriceSpan);

                var totalPriceSpan = parentLI.find($(".totalPrice"));
                console.log("totalPriceSpan");
                console.log(totalPriceSpan);

                $.ajax({
                    url: url,
                    type: "get",
                    data: "orderID=" + orderID + "&dishID=" + dishID,
                    success: function (data) {
                        console.log("Dish id=" + dishID + "has been successfully added/removed from order id=" + orderID);
                        console.log("data :");
                        console.log(data);

                        var dishesPrice = 0;
                        for(var i = 0; i < data.orderedDishes.length; i++) {

                            var currentOrderedDish = data.orderedDishes[i];
                            console.log("currentOrderedDish :");
                            console.log(currentOrderedDish);
                            console.log("currentOrderedDish.dish.id :");
                            console.log(currentOrderedDish.dish.id);
                            console.log(dishID);
                            console.log(currentOrderedDish.dish.id == dishID);
                            if(currentOrderedDish.dish.id == dishID) {
                                dishesPrice = currentOrderedDish.totalPrice;
                                break;
                            }
                        }
                        dishesPriceSpan.html(dishesPrice);
                        console.log("dishesPrice " + dishesPrice);

                        var totalPrice = data.totalPrice;
                        totalPriceSpan.html(totalPrice);
                        console.log("totalPrice " + totalPrice);
                    }
                });

                console.log("newValue " + newValue);
                if(newValue == 0) {
                    console.log("p");
                    console.log($(this).closest($("p")));
                    parentLI.remove($(this).closest($("p")));
                }

            }
        })
        .bind("keydown", function (event) {
            event.preventDefault();
        });

}
