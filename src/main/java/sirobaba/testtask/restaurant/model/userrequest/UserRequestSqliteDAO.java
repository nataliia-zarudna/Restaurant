package sirobaba.testtask.restaurant.model.userrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Nataliia on 30.07.2015.
 */
@Service("userRequestDAO")
public class UserRequestSqliteDAO implements UserRequestDAO {

    private static final String CREATE_USER_REQUEST_QUERY =
            "insert into user_requests (user_id, group_id) values(?, ?)";
    private static final String DELETE_USER_REQUEST_QUERY = "delete from user_requests " +
            " where user_id = ?" +
            "   and group_id = ?";
    private static final String FIND_BY_USER_ID_QUERY = "select user_id, group_id" +
            " from user_requests " +
            " where user_id = ?";
    private static final String FIND_BY_GROUP_ID_QUERY = "select user_id, group_id" +
            " from user_requests " +
            " where group_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createUserRequest(int userID, int groupID) {
        jdbcTemplate.update(CREATE_USER_REQUEST_QUERY, userID, groupID);
    }

    @Override
    public void deleteUserRequest(int userID, int groupID) {
        jdbcTemplate.update(DELETE_USER_REQUEST_QUERY, userID, groupID);
    }

    @Override
    public List<UserRequest> findByUserID(int userID) {
        List<UserRequest> userRequests = jdbcTemplate.query(FIND_BY_USER_ID_QUERY
                , new Object[]{userID}, new RequestRowMapper());
        return userRequests;
    }

    @Override
    public List<UserRequest> findByGroupID(int groupID) {
        List<UserRequest> userRequests = jdbcTemplate.query(FIND_BY_GROUP_ID_QUERY
                , new Object[]{groupID}, new RequestRowMapper());
        return userRequests;
    }

    private class RequestRowMapper implements RowMapper<UserRequest> {

        @Override
        public UserRequest mapRow(ResultSet resultSet, int i) throws SQLException {

            int userID = resultSet.getInt(1);
            int groupID = resultSet.getInt(2);

            return new UserRequest(userID, groupID);
        }
    }
}
