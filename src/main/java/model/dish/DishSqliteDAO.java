package model.dish;

import model.ModelException;
import model.SqliteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sirobaban on 27.07.2015.
 */
@Service("dishDAO")
public class DishSqliteDAO implements DishDAO {

    private static final Logger log = Logger.getLogger(DishSqliteDAO.class.getName());

    private static final String TABLE_NAME = "dishes";
    private static final String CREATE_QUERY = "insert into dishes(section_id, title, icon, price, description) values(?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "update dishes" +
            " set section_id = ? " +
            ", title = ? " +
            ", icon = ? " +
            ", price = ? " +
            ", description = ? " +
            "where id = ?";
    private static final String DELETE_BY_ID_QUERY = "delete from dishes" +
            " where id = ?";
    private static final String FIND_BY_ID_QUERY = "select id, section_id, title, icon, price, description " +
            " from dishes" +
            " where id = ?";
    private static final String FIND_BY_SECTION_ID_QUERY = "select id, section_id, title, icon, price, description " +
            " from dishes" +
            " where section_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Dish create(int sectionID, String title, String icon, double price, String description) throws ModelException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            connection = jdbcTemplate.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY);

            int columnIndex = 1;
            preparedStatement.setInt(columnIndex++, sectionID);
            preparedStatement.setString(columnIndex++, title);
            preparedStatement.setString(columnIndex++, icon);
            preparedStatement.setDouble(columnIndex++, price);
            preparedStatement.setString(columnIndex++, description);

            if (preparedStatement.executeUpdate() > 0) {

                return SqliteUtils.findLastInserted(connection, TABLE_NAME, new DishRowMapper());

            } else {
                throw new ModelException("No dish has been added");
            }

        } catch (SQLException e) {
            throw new ModelException("Exeption trying to create dish", e);
        } finally {
            SqliteUtils.closeResources(connection, preparedStatement, null);
        }
    }

    @Override
    public Dish update(int id, int sectionID, String title, String icon, double price, String description) throws ModelException {

        if(jdbcTemplate.update(UPDATE_QUERY, sectionID, title, icon, price, description, id) > 0) {
            return findByID(id);
        } else {
            throw new ModelException("No dish has been updated");
        }
    }

    @Override
    public void delete(int id) throws ModelException {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }

    @Override
    public Dish findByID(int id) throws ModelException {

        List<Dish> dishes = jdbcTemplate.query(FIND_BY_ID_QUERY, new Object[]{id}, new DishRowMapper());

        if (dishes != null && !dishes.isEmpty()) {
            return dishes.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Dish> findBySectionID(int sectionID) {
        List<Dish> dishes = jdbcTemplate.query(FIND_BY_SECTION_ID_QUERY, new Object[]{sectionID}, new DishRowMapper());

        return dishes;
    }

    public static class DishRowMapper implements RowMapper<Dish> {

        @Override
        public Dish mapRow(ResultSet resultSet, int i) throws SQLException {

            int id = resultSet.getInt(1);
            int sectionID = resultSet.getInt(2);
            String title = resultSet.getString(3);
            String icon = resultSet.getString(4);
            double price = resultSet.getDouble(5);
            String description = resultSet.getString(6);

            return new Dish(id, sectionID, title, icon, price, description);
        }
    }
}
