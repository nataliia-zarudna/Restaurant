package sirobaba.testtask.restaurant.model.dao.sqliteimpl;

import sirobaba.testtask.restaurant.model.ModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import sirobaba.testtask.restaurant.model.dao.SectionDAO;
import sirobaba.testtask.restaurant.model.entity.Section;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sirobaban on 27.07.2015.
 */
@Service(value = "sectionDAO")
public class SectionSqliteDAO implements SectionDAO {

    private static final String TABLE_NAME = "sections";
    private static final String CREATE_QUERY = "insert into sections(title) values(?)";
    private static final String UPDATE_QUERY = "update sections" +
            " set title = ? " +
            "where id = ?";
    private static final String FIND_BY_ID_QUERY = "select id, title " +
            " from sections" +
            " where id = ?";
    private static final String FIND_ALL_QUERY = "select id, title " +
            " from sections";
    private static final String DELETE_BY_ID_QUERY = "delete from sections" +
            " where id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Section create(String title) throws ModelException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {

            connection = jdbcTemplate.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement(CREATE_QUERY);

            preparedStatement.setString(1, title);

            if (preparedStatement.executeUpdate() > 0) {

                return SqliteUtils.findLastInserted(connection, TABLE_NAME, new SectionRowMapper());

            } else {
                throw new ModelException("No section has been added");
            }

        } catch (SQLException e) {
            throw new ModelException("Exeption trying to create section", e);
        } finally {
            SqliteUtils.closeResources(connection, preparedStatement, null);
        }
    }

    @Override
    public Section update(int id, String title) throws ModelException {

        if(jdbcTemplate.update(UPDATE_QUERY, title, id) > 0) {
            return findByID(id);
        } else {
            throw  new ModelException("Section id = " + id + " has not been updated");
        }
    }

    @Override
    public void delete(int id) throws ModelException {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }

    @Override
    public Section findByID(int id) throws ModelException {

        List<Section> sections = jdbcTemplate.query(FIND_BY_ID_QUERY, new Object[]{id}, new SectionRowMapper());

        if(!sections.isEmpty()) {
            return sections.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Section> findAll() throws ModelException {

        List<Section> sections = jdbcTemplate.query(FIND_ALL_QUERY, new Object[0], new SectionRowMapper());

        return sections;
    }

    private class SectionRowMapper implements RowMapper<Section> {

        @Override
        public Section mapRow(ResultSet resultSet, int i) throws SQLException {

            int columnIndex = 1;

            int id = resultSet.getInt(columnIndex++);
            String title = resultSet.getString(columnIndex++);

            return new Section(id, title);
        }
    }
}
