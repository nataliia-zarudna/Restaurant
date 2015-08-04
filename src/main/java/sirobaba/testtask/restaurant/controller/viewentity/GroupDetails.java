package sirobaba.testtask.restaurant.controller.viewentity;

import sirobaba.testtask.restaurant.model.entity.Group;
import sirobaba.testtask.restaurant.model.entity.User;

import java.util.List;

/**
 * Created by sirobaban on 30.07.2015.
 */
public class GroupDetails {

    private Group group;
    private User owner;
    private List<User> users;
    private List<User> userRequests;

    public GroupDetails(Group group, User owner, List<User> users) {
        this.group = group;
        this.owner = owner;
        this.users = users;
    }

    public GroupDetails(Group group, User owner, List<User> users, List<User> userRequests) {
        this(group, owner, users);
        this.userRequests = userRequests;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUserRequests() {
        return userRequests;
    }

    public void setUserRequests(List<User> userRequests) {
        this.userRequests = userRequests;
    }

    @Override
    public String toString() {
        return "GroupDetails{" +
                "group=" + group +
                ", owner=" + owner +
                ", users=" + users +
                ", userRequests=" + userRequests +
                '}';
    }
}
