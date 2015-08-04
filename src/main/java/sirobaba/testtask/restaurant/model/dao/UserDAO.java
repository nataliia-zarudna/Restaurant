package sirobaba.testtask.restaurant.model.dao;

import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.entity.User;

import java.util.List;

/**
 * Created by Nataliia on 27.07.2015.
 */
public interface UserDAO {

    User create(String firstName, String lastName, String password, String phone, String email, boolean isAdmin) throws ModelException;

    User update(int id, String firstName, String lastName, String password, String phone, String email, boolean isAdmin) throws ModelException;

    void delete(int id) throws ModelException;

    User findByID(int id) throws ModelException;

    User findByUsername(String username) throws ModelException;

    List<User> findByGroupID(int groupID) throws ModelException;

    List<User> findAll() throws ModelException;

}
