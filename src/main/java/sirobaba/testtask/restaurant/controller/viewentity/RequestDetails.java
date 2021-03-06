package sirobaba.testtask.restaurant.controller.viewentity;

import sirobaba.testtask.restaurant.model.entity.Group;
import sirobaba.testtask.restaurant.model.entity.User;

/**
 * Created by Nataliia on 31.07.2015.
 */
public class RequestDetails {

    private User user;
    private Group group;

    public RequestDetails(User user, Group group) {
        this.user = user;
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "RequestDetails{" +
                "user=" + user +
                ", group=" + group +
                '}';
    }
}
