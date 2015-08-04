package sirobaba.testtask.restaurant.model.dao;

import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.entity.Dish;

import java.util.List;

/**
 * Created by sirobaban on 27.07.2015.
 */
public interface DishDAO {

    Dish create(int sectionID, String title, String icon, double price, String description) throws ModelException;

    Dish update(int id, int sectionID, String title, String icon, double price, String description) throws ModelException;

    void delete(int id) throws ModelException;

    Dish findByID(int id) throws ModelException;

    List<Dish> findBySectionID(int sectionID);

}
