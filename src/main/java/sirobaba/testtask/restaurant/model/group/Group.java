package sirobaba.testtask.restaurant.model.group;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Nataliia on 27.07.2015.
 */
public class Group {

    private int id;

    @NotEmpty(message = "Group title must not be empty")
    private String title;
    private int ownerID;

    public Group() {}

    public Group(int id, String title, int ownerID) {
        this.id = id;
        this.title = title;
        this.ownerID = ownerID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (id != group.id) return false;
        if (ownerID != group.ownerID) return false;
        return !(title != null ? !title.equals(group.title) : group.title != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + ownerID;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ownerID=" + ownerID +
                '}';
    }
}
