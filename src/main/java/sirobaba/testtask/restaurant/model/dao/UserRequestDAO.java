package sirobaba.testtask.restaurant.model.dao;

import sirobaba.testtask.restaurant.model.entity.UserRequest;

import java.util.List;

/**
 * Created by Nataliia on 30.07.2015.
 */
public interface UserRequestDAO {

    void createUserRequest(int userID, int groupID);

    void deleteUserRequest(int userID, int groupID);

    List<UserRequest> findByUserID(int userID);

    List<UserRequest> findByGroupID(int groupID);

}
