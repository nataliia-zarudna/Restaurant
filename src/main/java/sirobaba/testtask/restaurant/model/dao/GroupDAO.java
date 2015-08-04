package sirobaba.testtask.restaurant.model.dao;

import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.entity.Group;

import java.util.List;

/**
 * Created by Nataliia on 27.07.2015.
 */
public interface GroupDAO {

    Group create(String title, int ownerID) throws ModelException;

    Group update(int id, String title, int ownerID) throws ModelException;

    void delete(int id);

    Group findByID(int id);

    List<Group> findByUserID(int userID);

    List<Group> findByOwnerID(int ownerID);

    List<Group> findAll();

    void addUserToGroup(int userID, int groupID);

    void removeUserFromGroup(int userID, int groupID) throws ModelException;

}
