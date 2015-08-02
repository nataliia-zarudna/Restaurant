package model;

import model.dish.Dish;
import model.order.Order;

import java.util.List;

/**
 * Created by Nataliia on 29.07.2015.
 */
public class OrderDetails {

    private Order order;
    private String orderStatus;
    private List<OrderedDish> orderedDishes;

    public OrderDetails(Order order, String orderStatus, List<OrderedDish> orderedDishes) {
        this.order = order;
        this.orderStatus = orderStatus;
        this.orderedDishes = orderedDishes;
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
