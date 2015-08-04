package sirobaba.testtask.restaurant.controller.viewentity;

import sirobaba.testtask.restaurant.model.entity.Dish;
import sirobaba.testtask.restaurant.model.entity.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nataliia on 29.07.2015.
 */
public class OrderDetails {

    private Order order;
    private String orderStatus;
    private List<OrderedDish> orderedDishes;

    public OrderDetails(Order order, String orderStatus, List<Dish> dishes) {
        this.order = order;
        this.orderStatus = orderStatus;
        fillOrderedDishes(dishes);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderedDish> getOrderedDishes() {
        return orderedDishes;
    }

    public void setOrderedDishes(List<OrderedDish> orderedDishes) {
        this.orderedDishes = orderedDishes;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean getIsPartOfGroupOrder() {
        boolean isPartOfGroupOrder = order.getGroupID() > 0;
        return isPartOfGroupOrder;
    }

    private void fillOrderedDishes(List<Dish> dishes) {

        Map<Dish, Integer> groupedDishes = new HashMap<Dish, Integer>();
        for (Dish dish : dishes) {

            int dishesAmount = 0;
            if (groupedDishes.containsKey(dish)) {

                dishesAmount = groupedDishes.get(dish);
            }
            groupedDishes.put(dish, ++dishesAmount);
        }

        this.orderedDishes = new ArrayList<OrderedDish>();
        for (Map.Entry<Dish, Integer> dishCountEntry : groupedDishes.entrySet()) {

            orderedDishes.add(new OrderedDish(dishCountEntry.getKey(), dishCountEntry.getValue()));
        }
    }


    public double getTotalPrice() {

        double totalPrice = 0;
        for(OrderedDish orderedDish : orderedDishes) {
            totalPrice += orderedDish.getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "order=" + order +
                ", orderStatus=" + orderStatus +
                ", orderedDishes=" + orderedDishes +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}
