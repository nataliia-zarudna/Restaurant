package sirobaba.testtask.restaurant.model;

import sirobaba.testtask.restaurant.model.dish.Dish;

/**
 * Created by Nataliia on 29.07.2015.
 */
public class OrderedDish {

    private Dish dish;
    private int count;

    public OrderedDish(Dish dish, int count) {
        this.dish = dish;
        this.count = count;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotalPrice() {
        return dish.getPrice() * count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedDish that = (OrderedDish) o;

        if (count != that.count) return false;
        return !(dish != null ? !dish.equals(that.dish) : that.dish != null);

    }

    @Override
    public int hashCode() {
        int result = dish != null ? dish.hashCode() : 0;
        result = 31 * result + count;
        return result;
    }

    @Override
    public String toString() {
        return "OrderedDish{" +
                "dish=" + dish +
                ", count=" + count +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}
