package sirobaba.testtask.restaurant.model;

import sirobaba.testtask.restaurant.model.group.Group;
import sirobaba.testtask.restaurant.model.group.GroupDAO;
import sirobaba.testtask.restaurant.model.user.User;
import sirobaba.testtask.restaurant.model.user.UserDAO;
import sirobaba.testtask.restaurant.model.userrequest.UserRequest;
import sirobaba.testtask.restaurant.model.userrequest.UserRequestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Nataliia on 28.07.2015.
 */
@Service("userManager")
public class UserManager implements UserDetailsService {

    public static final Logger log = Logger.getLogger(UserManager.class.getName());

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private GroupDAO groupDAO;
    @Autowired
    private UserRequestDAO userRequestDAO;

    public User createUser(String firstName, String lastName, String password, String phone, String email, boolean isAdmin) throws ModelException {
        return userDAO.create(firstName, lastName, password, phone, email, isAdmin);
    }

    public User updateUser(int id, String firstName, String lastName, String password, String phone, String email, boolean isAdmin) throws ModelException {
        return userDAO.update(id, firstName, lastName, password, phone, email, isAdmin);
    }

    public void deleteUser(int id) throws ModelException {
        userDAO.delete(id);
    }

    public Group createGroup(String title, int ownerID) throws ModelException {

        Group createdGroup = groupDAO.create(title, ownerID);
        userDAO.addUserToGroup(ownerID, createdGroup.getId());

        return createdGroup;
    }

    public Group updateGroup(int id, String title, int ownerID) throws ModelException {
        return groupDAO.update(id, title, ownerID);
    }

    public void deleteGroup(int groupID, int initiatorID) throws ModelException {

        Group group = getGroup(groupID);
        deleteGroup(group, initiatorID);
    }

    public void deleteGroup(Group group, int initiatorID) throws ModelException {

        if(group.getOwnerID() == initiatorID) {
            groupDAO.delete(group.getId());

        } else {
            throw new ModelException("Group can be deleted only by it's owner");
        }
    }

    public User getUser(int userID) throws ModelException {
        return userDAO.findByID(userID);
    }

    public Group getGroup(int groupID) {
        return groupDAO.findByID(groupID);
    }

    public List<Group> getUserGroups(int userID) {
        return groupDAO.findByUserID(userID);
    }

    public List<GroupDetails> getUserGroupDetails(int userID) throws ModelException {

        List<GroupDetails> groupDetailses = new ArrayList<GroupDetails>();

        List<Group> groups = getUserGroups(userID);
        for(Group group : groups) {

            List<User> groupUsers = getGroupUsers(group.getId());
            User groupOwner = userDAO.findByID(group.getOwnerID());
            GroupDetails groupDetails = new GroupDetails(group, groupOwner, groupUsers);

            if(group.getOwnerID() == userID) {
                List<UserRequest> userRequests = userRequestDAO.findByGroupID(group.getId());
                List<User> usersRequested = new ArrayList<User>();
                for (UserRequest request : userRequests) {
                    User userRequested = userDAO.findByID(request.getUserID());
                    usersRequested.add(userRequested);
                }
                groupDetails.setUserRequests(usersRequested);
            }

            groupDetailses.add(groupDetails);
        }

        return groupDetailses;
    }

    public List<GroupDetails> getAllGroupDetails() throws ModelException {

        List<GroupDetails> groupDetailses = new ArrayList<GroupDetails>();

        List<Group> groups = groupDAO.findAll();
        for(Group group : groups) {

            List<User> groupUsers = getGroupUsers(group.getId());
            User groupOwner = userDAO.findByID(group.getOwnerID());
            GroupDetails groupDetails = new GroupDetails(group, groupOwner, groupUsers);

            groupDetailses.add(groupDetails);
        }

        return groupDetailses;
    }

    public List<User> getAllUsers() throws ModelException {
        return userDAO.findAll();
    }

    public List<User> getGroupUsers(int groupID) throws ModelException {
        return userDAO.findByGroupID(groupID);
    }

    public List<Group> getNotUserGroups(int userID) throws ModelException {
        List<Group> userGroups = getUserGroups(userID);
        return getNotUserGroups(userGroups);
    }

    public List<Group> getNotUserGroups(List<Group> userGroups) throws ModelException {

        List<Group> allGroups = groupDAO.findAll();
        for(Group userGroup : userGroups) {
            allGroups.remove(userGroup);
        }

        return allGroups;
    }

    /*public List<UserRequest> getUserRequests(int userID) throws ModelException {
        return userRequestDAO.findByUserID(userID);
    }*/

    public List<Group> getRequestedGroups(int userID) throws ModelException {

        List<Group> requestedGroups = new ArrayList<Group>();

        List<UserRequest> userRequests = userRequestDAO.findByUserID(userID);
        for (UserRequest request : userRequests) {

            Group requestedGroup = groupDAO.findByID(request.getGroupID());
            requestedGroups.add(requestedGroup);
        }

        return requestedGroups;
    }

    public void createUserRequest(int userID, int groupID) throws ModelException {
        userRequestDAO.createUserRequest(userID, groupID);
    }

    public void acceptUserRequest(int userID, int groupID, int acceptedBy) throws ModelException {

        Group group = getGroup(groupID);
        if(group.getOwnerID() == acceptedBy) {

            userDAO.addUserToGroup(userID, groupID);
            userRequestDAO.deleteUserRequest(userID, groupID);

        } else {
            throw new ModelException("User request to enter group can be submitted only by its owner");
        }
    }

    public void removeUserRequest(int userID, int groupID, int removedBy) throws ModelException {

        Group group = getGroup(groupID);
        if(userID == removedBy || group.getOwnerID() == removedBy) {

            userRequestDAO.deleteUserRequest(userID, groupID);

        } else {
            throw new ModelException("User request to enter group can be removed only by user or group owner");
        }
    }

    public void removeUserFromGroup(int userID, int groupID, int removedBy) throws ModelException {

        Group group = getGroup(groupID);
        if(userID == removedBy || group.getOwnerID() == removedBy) {

            userDAO.removeUserFromGroup(userID, groupID);

        } else {
            throw new ModelException("User can be removed from group only by himself or group owner");
        }
    }

    public List<UserRequest> getUserRequestsToAccept(int ownerID) {

        List<UserRequest> requestsToAccept = new ArrayList<UserRequest>();

        List<Group> groups = groupDAO.findByOwnerID(ownerID);
        for(Group group : groups) {

            List<UserRequest> requests = userRequestDAO.findByGroupID(group.getId());
            requestsToAccept.addAll(requests);
        }

        return requestsToAccept;
    }

    /*public Map<Group,  List<User>> getUserRequestsToAccept(int ownerID) throws ModelException {

        Map<Group,  List<User>> userRequestsToAccept = new HashMap<Group,  List<User>>();

        List<Group> groups = groupDAO.findByOwnerID(ownerID);
        for (Group group : groups) {

            List<User> notAcceptedGroupUsers = getNotAcceptedGroupUsers(group.getId());
            userRequestsToAccept.put(group, notAcceptedGroupUsers);
        }

        return userRequestsToAccept;
    }*/

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        try {

            User user = userDAO.findByUsername(userName);
            if(user != null) {

                /*String role = user.getIsAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
                SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
                Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
                grantedAuthorities.add(grantedAuthority);

                return new org.springframework.security.core.userdetails.User(user.getUsername(),
                        user.getPassword(),
                        grantedAuthorities);
                        */
                return user;

            } else {
                throw new UsernameNotFoundException("User with username " + userName + " was not found");
            }

        } catch (ModelException e) {
            log.log(Level.SEVERE, "Error occured trying to load user by username " + userName, e);
            throw new UsernameNotFoundException("Error occured trying to load user by username " + userName, e);
        }
    }
}
