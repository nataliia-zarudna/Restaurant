package sirobaba.testtask.restaurant.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.entity.Group;
import sirobaba.testtask.restaurant.model.dao.GroupDAO;
import sirobaba.testtask.restaurant.model.entity.UserRequest;
import sirobaba.testtask.restaurant.model.dao.UserRequestDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nataliia on 04.08.2015.
 */
@Service("groupService")
public class GroupService {

    @Autowired
    private GroupDAO groupDAO;
    @Autowired
    private UserRequestDAO userRequestDAO;


    public Group createGroup(Group group) throws ModelException {

        Group createdGroup = groupDAO.create(group.getTitle(), group.getOwnerID());
        groupDAO.addUserToGroup(group.getOwnerID(), createdGroup.getId());

        return createdGroup;
    }

    public Group updateGroup(Group group) throws ModelException {
        return groupDAO.update(group.getId(), group.getTitle(), group.getOwnerID());
    }

    public void deleteGroup(int groupID) throws ModelException {
        groupDAO.delete(groupID);
    }

    public Group getGroup(int groupID) {
        return groupDAO.findByID(groupID);
    }

    public List<Group> getUserGroups(int userID) {
        return groupDAO.findByUserID(userID);
    }

    public List<Group> getAllGroups() throws ModelException {
        return groupDAO.findAll();
    }

    public List<UserRequest> getUserRequests(int groupID) {
        return userRequestDAO.findByGroupID(groupID);
    }

    public List<Group> getNotUserGroups(int userID) throws ModelException {
        List<Group> userGroups = getUserGroups(userID);
        return getNotUserGroups(userGroups);
    }

    public List<Group> getNotUserGroups(List<Group> userGroups) throws ModelException {

        List<Group> allGroups = groupDAO.findAll();
        for (Group userGroup : userGroups) {
            allGroups.remove(userGroup);
        }

        return allGroups;
    }

    public List<Group> getRequestedGroups(int userID) throws ModelException {

        List<Group> requestedGroups = new ArrayList<Group>();

        List<UserRequest> userRequests = userRequestDAO.findByUserID(userID);
        for (UserRequest request : userRequests) {

            Group requestedGroup = groupDAO.findByID(request.getGroupID());
            requestedGroups.add(requestedGroup);
        }

        return requestedGroups;
    }

    public List<UserRequest> getUserRequestsToAccept(int ownerID) {

        List<UserRequest> requestsToAccept = new ArrayList<UserRequest>();

        List<Group> groups = groupDAO.findByOwnerID(ownerID);
        for (Group group : groups) {

            List<UserRequest> requests = userRequestDAO.findByGroupID(group.getId());
            requestsToAccept.addAll(requests);
        }

        return requestsToAccept;
    }

    public void createUserRequest(int userID, int groupID) throws ModelException {
        userRequestDAO.createUserRequest(userID, groupID);
    }

    public void acceptUserRequest(int userID, int groupID, int acceptedBy) throws ModelException {

        Group group = getGroup(groupID);
        if (group.getOwnerID() == acceptedBy) {

            groupDAO.addUserToGroup(userID, groupID);
            userRequestDAO.deleteUserRequest(userID, groupID);

        } else {
            throw new ModelException("User request to enter group can be submitted only by its owner");
        }
    }

    public void removeUserRequest(int userID, int groupID, int removedBy) throws ModelException {

        Group group = getGroup(groupID);
        if (userID == removedBy || group.getOwnerID() == removedBy) {

            userRequestDAO.deleteUserRequest(userID, groupID);

        } else {
            throw new ModelException("User request to enter group can be removed only by user or group owner");
        }
    }

    public void removeUserFromGroup(int userID, int groupID, int removedBy) throws ModelException {

        Group group = getGroup(groupID);
        if (userID == removedBy || group.getOwnerID() == removedBy) {

            groupDAO.removeUserFromGroup(userID, groupID);

        } else {
            throw new ModelException("User can be removed from group only by himself or group owner");
        }
    }
}
