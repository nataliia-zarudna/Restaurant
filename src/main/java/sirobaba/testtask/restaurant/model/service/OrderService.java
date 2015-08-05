package sirobaba.testtask.restaurant.model.service;

//import com.sun.javafx.sg.PGShape;
import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.entity.Dish;
import sirobaba.testtask.restaurant.model.entity.Group;
import sirobaba.testtask.restaurant.model.entity.Order;
import sirobaba.testtask.restaurant.model.dao.OrderDAO;
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
    private GroupService groupService;

    public Order createUserOrder(int userID, Date reservationTime) throws ModelException {
        return orderDAO.create(DEFAULT_ORDER_NAME, userID, -1, NEW_ORDER_STATUS, reservationTime);
    }

    public Order createUserOrder(String title, int userID, Date reservationTime) throws ModelException {
        return orderDAO.create(title, userID, -1, NEW_ORDER_STATUS, reservationTime);
    }

    public Order createGroupOrder(Order groupOrder, int groupID) throws ModelException {
        return orderDAO.create(groupOrder.getTitle(), -1, groupID, NEW_ORDER_STATUS, groupOrder.getReservationTime());
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
        orderDAO.addDishToOrdered(orderID, userID, dishID);
    }

    public void removeDishFromOrder(int orderID, int userID, int dishID) throws ModelException {
        orderDAO.removeDishFromOrdered(orderID, userID, dishID);
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

        List<Group> groups = groupService.getUserGroups(userID);
        for (Group group : groups) {

            List<Order> groupOrders = orderDAO.findByGroupID(group.getId());
            allOrders.addAll(groupOrders);
        }

        return allOrders;
    }

    public List<Order> getAllNewOrdersByUser(int userID) throws ModelException {

        List<Order> allOrders = getAllOrdersByUser(userID);

        List<Order> newOrders = new ArrayList<Order>();
        for (Order order : allOrders) {
            if(order.getStatusID() == NEW_ORDER_STATUS) {
                newOrders.add(order);
            }
        }

        return newOrders;
    }

    public List<Order> getGroupOrders(int groupID) throws ModelException {
        return orderDAO.findByGroupID(groupID);
    }

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
                Group orderGroup = groupService.getGroup(order.getGroupID());
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
