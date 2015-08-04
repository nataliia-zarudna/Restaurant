package sirobaba.testtask.restaurant.model.entity;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by sirobaban on 27.07.2015.
 */
public class Dish {

    private int id;
    private int sectionID;
    private String title;
    private String icon;
    private double price;
    private String description;

    public Dish() {}

    public Dish(int id, int sectionID, String title, String icon, double price, String description) {
        this.id = id;
        this.sectionID = sectionID;
        this.title = title;
        this.icon = icon;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (id != dish.id) return false;
        if (sectionID != dish.sectionID) return false;
        if (Double.compare(dish.price, price) != 0) return false;
        if (title != null ? !title.equals(dish.title) : dish.title != null) return false;
        if (icon != null ? !icon.equals(dish.icon) : dish.icon != null) return false;
        return !(description != null ? !description.equals(dish.description) : dish.description != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + sectionID;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", sectionID=" + sectionID +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}
