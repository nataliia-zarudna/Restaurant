package sirobaba.testtask.restaurant.controller.viewentity;

import sirobaba.testtask.restaurant.model.entity.Dish;
import sirobaba.testtask.restaurant.model.entity.Order;
import sirobaba.testtask.restaurant.model.entity.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Nataliia on 31.07.2015.
 */
public class GroupOrderDetails {

    public static final Logger log = Logger.getLogger(GroupOrderDetails.class.getName());

    private Order order;
    private String orderStatus;
    private Map<String, OrderDetails> usersOrderedDetails;

    public GroupOrderDetails(Order order, String orderStatus, Map<User, List<Dish>> usersDishes) {
        this.order = order;
        this.orderStatus = orderStatus;
        fillUsersOrderedDetails(usersDishes);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Map<String, OrderDetails> getUsersOrderedDetails() {
        return usersOrderedDetails;
    }

    public OrderDetails getUserOrderDetails(User user) {
        try {
            return usersOrderedDetails.get(convertToJson(user));
        } catch (IOException e) {
            log.log(Level.SEVERE, "User object can not be converted to json", e);
            return null;
        }
    }

    public void setUsersOrderedDetails(Map<String, OrderDetails> usersOrderedDetails) {
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

    private void fillUsersOrderedDetails(Map<User, List<Dish>> usersDishes) {

        try {
            usersOrderedDetails = new HashMap<String, OrderDetails>();
            for (Map.Entry<User, List<Dish>> userDishesEntry : usersDishes.entrySet()) {

                OrderDetails userOrderDetails = new OrderDetails(order, orderStatus, userDishesEntry.getValue());
                User user = userDishesEntry.getKey();

                usersOrderedDetails.put(convertToJson(user), userOrderDetails);
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "User object can not be converted to json", e);
        }
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

    private String convertToJson(Object object) throws IOException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(object);
        return json;
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
