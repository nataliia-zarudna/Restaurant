package model.group;

import model.ModelException;

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

}