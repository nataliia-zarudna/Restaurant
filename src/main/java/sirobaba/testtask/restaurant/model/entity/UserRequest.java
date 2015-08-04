package sirobaba.testtask.restaurant.model.entity;

/**
 * Created by Nataliia on 30.07.2015.
 */
public class UserRequest {

    private int userID;
    private int groupID;

    public UserRequest(int userID, int groupID) {
        this.userID = userID;
        this.groupID = groupID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "userID=" + userID +
                ", groupID=" + groupID +
                '}';
    }
}
