package model.order;

import java.util.Date;

/**
 * Created by sirobaban on 27.07.2015.
 */
public class Order {

    private int id;
    private String title;
    private int userID;
    private int groupID;
    private int statusID;
    private Date reservationTime;

    public Order(int id, String title, int userID, int groupID, int statusID, Date reservationTime) {
        this.id = id;
        this.title = title;
        this.userID = userID;
        this.statusID = statusID;
        this.groupID = groupID;
        this.reservationTime = reservationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public Date getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", title=" + title +
                ", userID=" + userID +
                ", groupID=" + groupID +
                ", statusID=" + statusID +
                ", reservationTime=" + reservationTime +
                '}';
    }
}
