package model;

import model.dish.Dish;
import model.order.Order;
import model.user.User;

import java.util.*;

/**
 * Created by Nataliia on 31.07.2015.
 */
public class GroupOrderDetails {

    private Order order;
    private String orderStatus;
    private Map<User, OrderDetails> usersOrderedDetails;

    public GroupOrderDetails(Order order, String orderStatus, Map<User, OrderDetails> usersOrderedDetails) {
        this.order = order;
        this.orderStatus = orderStatus;
        this.usersOrderedDetails = usersOrderedDetails;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Map<User, OrderDetails> getUsersOrderedDetails() {
        return usersOrderedDetails;
    }

    public void setUsersOrderedDetails(Map<User, OrderDetails> usersOrderedDetails) {
        this.usersOrderedDetails = usersOrderedDetails;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalPrice() {

        double totalPrice = 0;
        for (OrderDetails userOrderedDetail : usersOrderedDetails.values()) {
            totalPrice += userOrderedDetail.getTotalPrice();
        }
        return totalPrice;
    }

    public Collection<OrderedDish> getOrderedDishes() {

        Map<Dish, OrderedDish> orderedDishs = new HashMap<Dish, OrderedDish>();
        for (OrderDetails orderDetails : usersOrderedDetails.values()) {
            for (OrderedDish orderedDish : orderDetails.getOrderedDishes()) {

                int count = orderedDish.getCount();
                if (orderedDishs.containsKey(orderedDish.getDish())) {

                    count += orderedDishs.get(orderedDish.getDish()).getCount();
                    orderedDish.setCount(count);
                }
                orderedDishs.put(orderedDish.getDish(), orderedDish);
            }
        }

        return orderedDishs.values();
    }

    @Override
    public String toString() {
        return "GroupOrderDetails{" +
                "order=" + order +
                "orderStatus=" + orderStatus +
                ", usersOrderedDetails=" + usersOrderedDetails +
                '}';
    }
}
