package model.group;

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
@Service("groupDAO")
public class GroupSqliteDAO implements GroupDAO {

    private static final Logger log = Logger.getLogger(GroupSqliteDAO.class.getName());

    private static final String TABLE_NAME = "groups";
    private static final String CREATE_QUERY = "insert into groups (title, owner_id) values(?, ?)";
    private static final String UPDATE_QUERY = "update groups " +
            " set title = ? " +
            ", owner_id = ? " +
            " where id = ?";
    private static final String DELETE_BY_ID_QUERY = "delete from groups " +
            " where id = ?";
    private static final String FIND_BY_ID_QUERY = "select id, title, owner_id" +
            " from groups " +
            " where id = ?";
    private static final String FIND_BY_USER_ID_QUERY = "select id, title, owner_id" +
            " from user_group_relations rel " +
            "   ,groups group_ " +
            " where rel.user_id = ? " +
            "   and group_.id = rel.group_id";
    private static final String FIND_BY_OWNER_ID_QUERY = "select id, title, owner_id" +
            " from groups " +
            " where owner_id = ?";
    private static final String FIND_ALL = "select id, title, owner_id" +
            " from groups";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Group create(String title, int ownerID) throws ModelException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            connection = jdbcTemplate.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY);

            int columnIndex = 1;
            preparedStatement.setString(columnIndex++, title);
            preparedStatement.setInt(columnIndex++, ownerID);

            if (preparedStatement.executeUpdate() > 0) {

                return SqliteUtils.findLastInserted(connection, TABLE_NAME, new GroupRowMapper());

            } else {
                throw new ModelException("No group has been added");
            }

        } catch (SQLException e) {
            throw new ModelException("Exeption trying to create group", e);
        } finally {
            SqliteUtils.closeResources(connection, preparedStatement, null);
        }
    }

    @Override
    public Group update(int id, String title, int ownerID) throws ModelException {
        if(jdbcTemplate.update(UPDATE_QUERY, title, ownerID, id) > 0) {
            return findByID(id);
        } else {
            throw  new ModelException("No group has been added");
        }
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }

    @Override
    public Group findByID(int id) {
        List<Group> groups = jdbcTemplate.query(FIND_BY_ID_QUERY, new Object[] { id }, new GroupRowMapper());

        if(groups != null && !groups.isEmpty()) {
            return groups.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Group> findByUserID(int userID) {
        List<Group> groups = jdbcTemplate.query(FIND_BY_USER_ID_QUERY, new Object[] { userID }, new GroupRowMapper());
        return groups;
    }

    @Override
    public List<Group> findByOwnerID(int ownerID) {
        List<Group> groups = jdbcTemplate.query(FIND_BY_OWNER_ID_QUERY, new Object[] { ownerID }, new GroupRowMapper());
        return groups;
    }

    @Override
    public List<Group> findAll() {
        List<Group> groups = jdbcTemplate.query(FIND_ALL, new Object[0], new GroupRowMapper());
        return groups;
    }

    private class GroupRowMapper implements RowMapper<Group> {

        @Override
        public Group mapRow(ResultSet resultSet, int i) throws SQLException {

            int columnIndex = 1;

            int id = resultSet.getInt(columnIndex++);
            String title = resultSet.getString(columnIndex++);
            int ownerID = resultSet.getInt(columnIndex++);

            return new Group(id, title, ownerID);
        }
    }
}
