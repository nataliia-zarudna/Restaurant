package sirobaba.testtask.restaurant.model.dao.sqliteimpl;

import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.dao.OrderDAO;
import sirobaba.testtask.restaurant.model.entity.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import sirobaba.testtask.restaurant.model.entity.Order;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Nataliia on 27.07.2015.
 */
@Service("orderDAO")
public class OrderSqliteDAO implements OrderDAO {

    private static final Logger log = Logger.getLogger(OrderSqliteDAO.class.getName());

    private static final String TABLE_NAME = "orders";
    private static final String CREATE_QUERY = "insert into orders (title, user_id, group_id, status_id, reservation_time)" +
            " values(?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "update orders " +
            " set title = ? " +
            ", user_id = ? " +
            ", group_id = ? " +
            ", status_id = ? " +
            ", reservation_time = ? " +
            "where id = ?";
    private static final String DELETE_BY_ID_QUERY = "delete from orders " +
            " where id = ?";
    private static final String FIND_BY_ID_QUERY = "select id, title, user_id, group_id, status_id, reservation_time " +
            " from orders " +
            " where id = ?";
    private static final String FIND_BY_GROUP_ID_QUERY = "select id, title, user_id, group_id, status_id, reservation_time " +
            " from orders " +
            " where group_id = ?";
    private static final String FIND_BY_USER_ID_QUERY = "select id, title, user_id, group_id, status_id, reservation_time " +
            " from orders " +
            " where user_id = ?";
    private static final String FIND_USER_ORDERS_QUERY = "select id, title, user_id, group_id, status_id, reservation_time " +
            " from orders " +
            " where user_id is not null ";
    private static final String FIND_GROUP_ORDERS_QUERY = "select id, title, user_id, group_id, status_id, reservation_time " +
            " from orders " +
            " where group_id is not null ";
    private static final String ADD_DISH_TO_ORDER_QUERY = "insert into ordered_dishes(order_id, user_id, dish_id) values(?, ?, ?)";
    private static final String DELETE_DISH_FROM_ORDER_QUERY = "delete from ordered_dishes " +
            " where order_id = ?" +
            "   and user_id = ?" +
            "   and dish_id = ?" +
            "   and rowid = (select rowid from ordered_dishes " +
            "                where order_id = ? " +
            "                   and user_id = ? " +
            "                   and dish_id = ? " +
            "                limit 1)";
    private static final String FIND_DISHES_BY_ORDER_AND_USER_QUERY = "select id, section_id, title, icon, price, description" +
            " from ordered_dishes ordered_dish" +
            "   , dishes dish " +
            " where ordered_dish.order_id = ?" +
            "   and ordered_dish.user_id = ? " +
            "   and dish.id = ordered_dish.dish_id ";
    private static final String FIND_DISHES_BY_ORDER = "select id, section_id, title, icon, price, description" +
            " from ordered_dishes ordered_dish" +
            "   , dishes dish " +
            " where ordered_dish.order_id = ?" +
            "   and dish.id = ordered_dish.dish_id ";
    private static final String FIND_ORDER_STATUS_STRING = "select status.value " +
            " from order_statuses status " +
            " where status.id = ? ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Order create(String title, int userID, int groupID, int statusID, Date reservationTime) throws ModelException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            connection = jdbcTemplate.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY);

            int columnIndex = 1;
            preparedStatement.setString(columnIndex++, title);

            if(userID > 0) {
                preparedStatement.setInt(columnIndex++, userID);
            } else {
                preparedStatement.setNull(columnIndex++, Types.INTEGER);
            }
            if(groupID > 0) {
                preparedStatement.setInt(columnIndex++, groupID);
            } else {
                preparedStatement.setNull(columnIndex++, Types.INTEGER);
            }

            preparedStatement.setInt(columnIndex++, statusID);
            preparedStatement.setDate(columnIndex++, new java.sql.Date(reservationTime.getTime()));

            if (preparedStatement.executeUpdate() > 0) {

                return SqliteUtils.findLastInserted(connection, TABLE_NAME, new OrderRowMapper());

            } else {
                throw new ModelException("No order has been added");
            }

        } catch (SQLException e) {
            throw new ModelException("Exeption trying to create order", e);
        } finally {
            SqliteUtils.closeResources(connection, preparedStatement, null);
        }
    }

    @Override
    public Order update(int id, String title, int userID, int groupID, int statusID, Date reservationTime) throws ModelException {

        Integer userIDParam = (userID > 0 ? userID : null);
        Integer groupIDParam = (groupID > 0 ? groupID : null);

        if (jdbcTemplate.update(UPDATE_QUERY, title, userIDParam, groupIDParam, statusID, reservationTime, id) > 0) {
            return findByID(id);
        } else {
            throw new ModelException("No order has been updates");
        }
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }

    @Override
    public Order findByID(int id) {
        List<Order> orders = jdbcTemplate.query(FIND_BY_ID_QUERY, new Object[]{id}, new OrderRowMapper());

        if (orders != null && !orders.isEmpty()) {
            return orders.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Order> findByGroupID(int groupID) {
        return jdbcTemplate.query(FIND_BY_GROUP_ID_QUERY, new Object[]{groupID}, new OrderRowMapper());
    }

    @Override
    public List<Order> findByUserID(int userID) {
        return jdbcTemplate.query(FIND_BY_USER_ID_QUERY, new Object[]{userID}, new OrderRowMapper());
    }

    @Override
    public List<Order> findGroupOrders() throws ModelException {
        return jdbcTemplate.query(FIND_GROUP_ORDERS_QUERY, new Object[]{}, new OrderRowMapper());
    }

    @Override
    public List<Order> findUserOrders() throws ModelException {
        return jdbcTemplate.query(FIND_USER_ORDERS_QUERY, new Object[]{}, new OrderRowMapper());
    }

    @Override
    public void addDishToOrdered(int orderID, int userID, int groupID) {
        jdbcTemplate.update(ADD_DISH_TO_ORDER_QUERY, orderID, userID, groupID);
    }

    @Override
    public void removeDishFromOrdered(int orderID, int userID, int groupID) throws ModelException {
        jdbcTemplate.update(DELETE_DISH_FROM_ORDER_QUERY, orderID, userID, groupID, orderID, userID, groupID);
    }

    @Override
    public List<Dish> getDishesByOrder(int orderID) throws ModelException {
        List<Dish> dishes = jdbcTemplate.query(FIND_DISHES_BY_ORDER
                , new Object[]{ orderID }
                , new DishSqliteDAO.DishRowMapper());
        return dishes;
    }

    @Override
    public List<Dish> getDishesByOrderAndUser(int orderID, int userID) {
        List<Dish> dishes = jdbcTemplate.query(FIND_DISHES_BY_ORDER_AND_USER_QUERY
                , new Object[]{ orderID, userID }
                , new DishSqliteDAO.DishRowMapper());
        return dishes;
    }

    @Override
    public String getOrderStatusStringRepresentation(int id) throws ModelException {
        String status = jdbcTemplate.queryForObject(FIND_ORDER_STATUS_STRING
                , new Object[]{id}, String.class);

        if(status != null && !status.isEmpty()) {
            return status;
        } else {
            throw new ModelException("Order id=" + id + " has incorrect status");
        }
    }

    private class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet resultSet, int i) throws SQLException {

            int columnIndex = 1;

            int id = resultSet.getInt(columnIndex++);
            String title = resultSet.getString(columnIndex++);
            int userID = resultSet.getInt(columnIndex++);
            int groupID = resultSet.getInt(columnIndex++);
            int statusID = resultSet.getInt(columnIndex++);
            Date reservationTime = resultSet.getDate(columnIndex++);

            return new Order(id, title, userID, groupID, statusID, new Date(reservationTime.getTime()));
        }
    }
}
