package model;

import com.sun.javafx.sg.PGShape;
import model.dish.Dish;
import model.group.Group;
import model.order.Order;
import model.order.OrderDAO;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Nataliia on 28.07.2015.
 */
@Service("orderService")
public class OrderService {

    public static final String DEFAULT_ORDER_NAME = "My Order";
    public static final int NEW_ORDER_STATUS = 1;
    public static final int SUBMITTED_ORDER_STATUS = 2;
    public static final int CLOSE_ORDER_STATUS = 3;

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private UserManager userManager;

    public Order createUserOrder(int userID, Date reservationTime) throws ModelException {
        return orderDAO.create(DEFAULT_ORDER_NAME, userID, -1, NEW_ORDER_STATUS, reservationTime);
    }

    public Order createUserOrder(String title, int userID, Date reservationTime) throws ModelException {
        return orderDAO.create(title, userID, -1, NEW_ORDER_STATUS, reservationTime);
    }

    public Order createGroupOrder(String title, int groupID, Date reservationTime) throws ModelException {
        return orderDAO.create(title, -1, groupID, NEW_ORDER_STATUS, reservationTime);
    }

    public Order updateOrder(Order newOrder) throws ModelException {
        return orderDAO.update(newOrder.getId(), newOrder.getTitle()
                , newOrder.getUserID(), newOrder.getGroupID(), newOrder.getStatusID(), newOrder.getReservationTime());
    }

    public Order updateOrder(int id, String title, Date reservationTime) throws ModelException {
        Order oldOrder = orderDAO.findByID(id);
        return orderDAO.update(id, title, oldOrder.getUserID(), oldOrder.getGroupID(), oldOrder.getStatusID(), reservationTime);
    }

    private void deleteOrder(int id) throws ModelException {
        orderDAO.delete(id);
    }

    public void addDishToOrder(int orderID, int userID, int dishID) throws ModelException {
        orderDAO.addDish(orderID, userID, dishID);
    }

    public Order getOrder(int orderID) throws ModelException {
        return orderDAO.findByID(orderID);
    }

    public List<Dish> getOrderedDishes(int orderID) throws ModelException {
        return orderDAO.getDishesByOrder(orderID);
    }

    public List<Dish> getOrderedDishes(int orderID, int userID) throws ModelException {
        return orderDAO.getDishesByOrderAndUser(orderID, userID);
    }

   /* public Map<Dish, Integer> getGroupedOrderedDishes(int orderID, int userID) throws ModelException {

        Map<Dish, Integer> groupedDishes = new HashMap<Dish, Integer>();
        List<Dish> dishes = orderDAO.getDishesByOrderAndUser(orderID, userID);

        for (Dish dish : dishes) {

            int dishesAmount = 0;
            if(groupedDishes.containsKey(dish)) {

                dishesAmount = groupedDishes.get(dish);
            }
            groupedDishes.put(dish, ++dishesAmount);
        }

        return groupedDishes;
    }*/

   /* public OrderDetails getOrderDetails(int orderID, int userID) throws ModelException {

        Order order = orderDAO.findByID(orderID);
        String orderStatus = getOrderStatusStringRepresentation(orderID);
        List<Dish> dishes = orderDAO.getDishesByOrderAndUser(orderID, userID);
        List<OrderedDish> orderedDishes = transphomToOderedDishes(dishes);

        return new OrderDetails(order, orderStatus, orderedDishes);
    }*/

    public String getOrderStatusStringRepresentation(int orderStatusID) throws ModelException {
        return orderDAO.getOrderStatusStringRepresentation(orderStatusID);
    }

    public List<Order> getUserOrders(int userID) throws ModelException {
        return orderDAO.findByUserID(userID);
    }

    public List<Order> getAllUserOrders() throws ModelException {
        return orderDAO.findUserOrders();
    }

    public List<Order> getAllGroupOrders() throws ModelException {
        return orderDAO.findGroupOrders();
    }

    public List<Order> getAllOrdersByUser(int userID) throws ModelException {

        List<Order> allOrders = getUserOrders(userID);

        List<Group> groups = userManager.getUserGroups(userID);
        for (Group group : groups) {

            List<Order> groupOrders = orderDAO.findByGroupID(group.getId());
            allOrders.addAll(groupOrders);
        }

        return allOrders;
    }

   /* public List<OrderDetails> getUserOrderDetails(int userID) throws ModelException {

        List<OrderDetails> orderDetailses = new ArrayList<OrderDetails>();

        List<Order> userOrders = getUserOrders(userID);
        for (Order order : userOrders) {

            String orderStatus = getOrderStatusStringRepresentation(order.getId());
            List<Dish> dishes = orderDAO.getDishesByOrderAndUser(order.getId(), userID);
            OrderDetails orderDetails = new OrderDetails(order, orderStatus, transphomToOderedDishes(dishes));

            orderDetailses.add(orderDetails);
        }

        return orderDetailses;
    }*/

    public List<Order> getGroupOrders(int groupID) throws ModelException {
        return orderDAO.findByGroupID(groupID);
    }

    /*
    public GroupOrderDetails getGroupOrderDetails(int orderID) throws ModelException {

        Order order = getOrder(orderID);
        if (isGroupOrder(orderID)) {

            String orderStatus = getOrderStatusStringRepresentation(order.getId());

            Map<User, OrderDetails> usersOrderedDishes = new HashMap<User, OrderDetails>();
            List<User> groupUsers = userManager.getGroupUsers(order.getGroupID());
            for (User groupUser : groupUsers) {

                List<Dish> dishes = orderDAO.getDishesByOrderAndUser(order.getId(), groupUser.getId());
                List<OrderedDish> orderedDishs = transphomToOderedDishes(dishes);
                usersOrderedDishes.put(groupUser, new OrderDetails(order, orderStatus, orderedDishs));
            }

            GroupOrderDetails orderDetails = new GroupOrderDetails(order, orderStatus, usersOrderedDishes);

            return orderDetails;

        } else {
            throw new ModelException("Order " + order + " does not belong to group");
        }
    }

    public List<GroupOrderDetails> getGroupOrderDetailsByUser(int userID) throws ModelException {

        List<GroupOrderDetails> orderDetailses = new ArrayList<GroupOrderDetails>();

        List<Group> groups = userManager.getUserGroups(userID);
        for (Group group : groups) {

            List<User> groupUsers = userManager.getGroupUsers(group.getId());
            List<Order> groupOrders = getGroupOrders(group.getId());
            for (Order order : groupOrders) {

                String orderStatus = getOrderStatusStringRepresentation(order.getId());
                Map<User, OrderDetails> usersOrderedDishes = new HashMap<User, OrderDetails>();
                for (User groupUser : groupUsers) {

                    List<Dish> dishes = orderDAO.getDishesByOrderAndUser(order.getId(), groupUser.getId());
                    List<OrderedDish> orderedDishs = transphomToOderedDishes(dishes);
                    usersOrderedDishes.put(groupUser, new OrderDetails(order, orderStatus, orderedDishs));
                }

                GroupOrderDetails orderDetails = new GroupOrderDetails(order, orderStatus, usersOrderedDishes);

                orderDetailses.add(orderDetails);
            }
        }

        return orderDetailses;
    }
*/

    public Order checkout(int orderID, int initiatorID) throws ModelException {

        Order order = getOrder(orderID);
        if (canUserModifyOrder(order, initiatorID)) {
            order.setStatusID(SUBMITTED_ORDER_STATUS);
            return updateOrder(order);

        } else {
            throw new ModelException("User id=" + initiatorID + " is not allowed to modify order " + order.getTitle());
        }
    }

    public void cancelOrder(int orderID, int initiatorID) throws ModelException {

        Order order = getOrder(orderID);
        if (canUserModifyOrder(order, initiatorID)) {

            deleteOrder(order.getId());

        } else {
            throw new ModelException("User id=" + initiatorID + " is not allowed to modify order " + order.getTitle());
        }
    }

    private boolean canUserModifyOrder(Order order, int initiatorID) throws ModelException {

        if (order.getUserID() != initiatorID) {

            if (isGroupOrder(order.getId())) {
                Group orderGroup = userManager.getGroup(order.getGroupID());
                if (orderGroup.getOwnerID() != initiatorID) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    public boolean isGroupOrder(int orderID) throws ModelException {

        Order order = getOrder(orderID);
        return order.getGroupID() > 0;
    }

   /* public double getPrice(Dish dish, int count) {
        return dish.getPrice() * count;
    }

    public double getTotalPrice(List<Dish> dishes) {

        double totalPrice = 0;
        for(Dish dish : dishes) {
            totalPrice += dish.getPrice();
        }
        return totalPrice;
    }*/
}
