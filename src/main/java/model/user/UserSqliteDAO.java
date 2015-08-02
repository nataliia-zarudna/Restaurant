package model.user;

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
 * Created by Nataliia on 27.07.2015.
 */
@Service("userDAO")
public class UserSqliteDAO implements UserDAO {

    private static final Logger log = Logger.getLogger(UserSqliteDAO.class.getName());

    private static final String TABLE_NAME = "users";
    private static final String CREATE_QUERY = "insert into users (first_name, last_name, password, phone, email, is_admin) values(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "update users " +
            " set first_name = ? " +
            ", last_name = ? " +
            ", phone = ? " +
            ", email = ? " +
            ", is_admin = ? " +
            "where id = ?";
    private static final String DELETE_BY_ID_QUERY = "delete from users " +
            " where id = ?";
    private static final String FIND_BY_ID_QUERY = "select id, first_name, last_name, password, phone, email, is_admin" +
            " from users " +
            " where id = ?";
    private static final String FIND_BY_USERNAME_QUERY = "select id, first_name, last_name, password, phone, email, is_admin" +
            " from users " +
            " where email = ?";
    private static final String FIND_ALL_QUERY = "select id, first_name, last_name, password, phone, email, is_admin" +
            " from users ";
    private static final String FIND_BY_GROUP_ID_QUERY = "select id, first_name, last_name, password, phone, email, is_admin" +
            " from user_group_relations rel" +
            "   ,users user" +
            " where rel.group_id = ?" +
            "   and user.id = rel.user_id";
    private static final String CREATE_USER_GROUP_RELATION_QUERY =
            "insert into user_group_relations (user_id, group_id) values(?, ?)";
    private static final String DELETE_USER_GROUP_RELATION_QUERY = "delete from user_group_relations " +
            " where user_id = ?" +
            "   and group_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User create(String firstName, String lastName, String password, String phone, String email, boolean isAdmin) throws ModelException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            connection = jdbcTemplate.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY);

            int columnIndex = 1;
            preparedStatement.setString(columnIndex++, firstName);
            preparedStatement.setString(columnIndex++, lastName);
            preparedStatement.setString(columnIndex++, password);
            preparedStatement.setString(columnIndex++, phone);
            preparedStatement.setString(columnIndex++, email);
            preparedStatement.setBoolean(columnIndex++, isAdmin);

            if (preparedStatement.executeUpdate() > 0) {

                return SqliteUtils.findLastInserted(connection, TABLE_NAME, new UserRowMapper());

            } else {
                throw new ModelException("No user has been added");
            }

        } catch (SQLException e) {
            throw new ModelException("Exeption trying to create user", e);
        } finally {
            SqliteUtils.closeResources(connection, preparedStatement, null);
        }
    }

    @Override
    public User update(int id, String firstName, String lastName, String password, String phone, String email, boolean isAdmin) throws ModelException {
        if (jdbcTemplate.update(UPDATE_QUERY, firstName, lastName, password, phone, email, isAdmin, id) > 0) {
            return findByID(id);
        } else {
            throw new ModelException("No user has been updated");
        }
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }

    @Override
    public User findByID(int id) {
        List<User> users = jdbcTemplate.query(FIND_BY_ID_QUERY, new Object[]{id}, new UserRowMapper());

        if (users != null && !users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User findByUsername(String username) throws ModelException {
        List<User> users = jdbcTemplate.query(FIND_BY_USERNAME_QUERY, new Object[] { username }, new UserRowMapper());

        if (users != null && !users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<User> findByGroupID(int groupID) {
        List<User> users = jdbcTemplate.query(FIND_BY_GROUP_ID_QUERY, new Object[]{groupID}, new UserRowMapper());
        return users;
    }

    @Override
    public List<User> findAll() {
        List<User> users = jdbcTemplate.query(FIND_ALL_QUERY, new Object[0], new UserRowMapper());
        return users;
    }

    @Override
    public void addUserToGroup(int userID, int groupID) {
        jdbcTemplate.update(CREATE_USER_GROUP_RELATION_QUERY, userID, groupID);
    }

    @Override
    public void removeUserFromGroup(int userID, int groupID) {
        jdbcTemplate.update(DELETE_USER_GROUP_RELATION_QUERY, userID, groupID);
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {

            int columnIndex = 1;

            int id = resultSet.getInt(columnIndex++);
            String firstName = resultSet.getString(columnIndex++);
            String lastName = resultSet.getString(columnIndex++);
            String password = resultSet.getString(columnIndex++);
            String phone = resultSet.getString(columnIndex++);
            String email = resultSet.getString(columnIndex++);
            boolean isAdmin = resultSet.getBoolean(columnIndex++);

            return new User(id, firstName, lastName, password, phone, email, isAdmin);
        }
    }
}
