package sirobaba.testtask.restaurant.model.dao.sqliteimpl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import sirobaba.testtask.restaurant.model.ModelException;

import javax.sql.DataSource;
import java.sql.*;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Nataliia on 27.07.2015.
 */
public class SqliteUtils {

    private static final Logger log = Logger.getLogger(SqliteUtils.class.getName());

    private static final String FIND_LAST_INSERTED_QUERY = "select * " +
            " from {0}" +
            " where rowid = last_insert_rowid();";

    public static <T> T findLastInserted(Connection connection, String tableName, RowMapper<T> rowMapper) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            preparedStatement = connection.prepareStatement(MessageFormat.format(FIND_LAST_INSERTED_QUERY, tableName));
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                T createdObject = rowMapper.mapRow(resultSet, 0);
                return createdObject;

            } else {
                return null;
            }

        } finally {
            closeResources(null, preparedStatement, resultSet);
        }
    }

    public static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.log(Level.WARNING, e.getMessage(), e);
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.log(Level.WARNING, e.getMessage(), e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.log(Level.WARNING, e.getMessage(), e);
            }
        }
    }


}
