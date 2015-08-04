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

    private static final String DEFAULT_ID_COLUMN_NAME = "id";
    private static final String JDBC_CLASS_NAME = "org.sqlite.JDBC";
    //private static final String RESTAURANT_DB_URL = "jdbc:sqlite:restaurant";
    private static final String RESTAURANT_DB_URL = "jdbc:sqlite:d://sql/restaurant.sqlite";

    private static final String DELETE_EXCEPTION_MESSAGE = "Exception trying to delete dish with id ";

    private static final String FIND_LAST_INSERTED_QUERY = "select * " +
            " from {0}" +
            " where rowid = last_insert_rowid();";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {

        Class.forName(JDBC_CLASS_NAME);
        return DriverManager.getConnection(RESTAURANT_DB_URL);
    }

    public static void deleteByID(String tableName, int id) throws ModelException {
        deleteByID(tableName, id, DEFAULT_ID_COLUMN_NAME);
    }

    public static void deleteByID(String tableName, int id, String idColumnName) throws ModelException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = getConnection();

            preparedStatement = connection.prepareStatement("delete from " + tableName + " where " + idColumnName + " = ?");
            preparedStatement.setInt(1, id);

            if(preparedStatement.executeUpdate() == 0) {
                throw new ModelException("There was no objects with id " + id + " in " + tableName + " table");
            }

        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, e.getMessage() + id, e);
            throw new ModelException(DELETE_EXCEPTION_MESSAGE + id, e);
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage() + id, e);
            throw new ModelException(DELETE_EXCEPTION_MESSAGE + id, e);
        } finally {
            closeResources(connection, preparedStatement, null);
        }
    }

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
