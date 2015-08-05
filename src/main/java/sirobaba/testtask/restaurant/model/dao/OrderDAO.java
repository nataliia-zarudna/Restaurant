package sirobaba.testtask.restaurant.model.dao;

import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.entity.Dish;
import sirobaba.testtask.restaurant.model.entity.Order;

import java.util.Date;
import java.util.List;

/**
 * Created by Nataliia on 27.07.2015.
 */
public interface OrderDAO {

    Order create(String title, int userID, int groupID, int statusID, Date reservationTime) throws ModelException;

    Order update(int id, String title, int userID, int groupID, int statusID, Date reservationTime) throws ModelException;

    void delete(int id) throws ModelException;

    Order findByID(int id) throws ModelException;

    List<Order> findByGroupID(int groupID) throws ModelException;

    List<Order> findByUserID(int userID) throws ModelException;

    List<Order> findGroupOrders() throws ModelException;

    List<Order> findUserOrders() throws ModelException;

    void addDishToOrdered(int orderID, int userID, int groupID) throws ModelException;

    void removeDishFromOrdered(int orderID, int userID, int groupID) throws ModelException;

    List<Dish> getDishesByOrder(int orderID) throws ModelException;

    List<Dish> getDishesByOrderAndUser(int orderID, int userID) throws ModelException;

    String getOrderStatusStringRepresentation(int id) throws ModelException;
}
