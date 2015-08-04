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
@Service("userService")
public class UserService implements UserDetailsService {

    public static final Logger log = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserDAO userDAO;

    public User createUser(User user) throws ModelException {
        return createUser(user.getFirstName(), user.getLastName(), user.getPassword()
                , user.getPhone(), user.getEmail(), user.getIsAdmin());
    }

    public User createUser(String firstName, String lastName, String password, String phone, String email, boolean isAdmin) throws ModelException {
        return userDAO.create(firstName, lastName, password, phone, email, isAdmin);
    }

    public User updateUser(User user) throws ModelException {
        return updateUser(user.getId(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getPhone(), user.getEmail(), user.getIsAdmin());
    }

    public User updateUser(int id, String firstName, String lastName, String password, String phone, String email, boolean isAdmin) throws ModelException {
        return userDAO.update(id, firstName, lastName, password, phone, email, isAdmin);
    }

    public void deleteUser(int id) throws ModelException {
        userDAO.delete(id);
    }

    public User getUser(int userID) throws ModelException {
        return userDAO.findByID(userID);
    }

    public List<User> getAllUsers() throws ModelException {
        return userDAO.findAll();
    }

    public List<User> getGroupUsers(int groupID) throws ModelException {
        return userDAO.findByGroupID(groupID);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        try {

            User user = userDAO.findByUsername(userName);
            if (user != null) {
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
