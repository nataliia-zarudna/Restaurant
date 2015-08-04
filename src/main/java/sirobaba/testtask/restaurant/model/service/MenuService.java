package sirobaba.testtask.restaurant.model.service;

import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.entity.Dish;
import sirobaba.testtask.restaurant.model.dao.DishDAO;
import sirobaba.testtask.restaurant.model.entity.Section;
import sirobaba.testtask.restaurant.model.dao.SectionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nataliia on 28.07.2015.
 */
@Service("menuService")
public class MenuService {

    @Autowired
    private SectionDAO sectionDAO;
    @Autowired
    private DishDAO dishDAO;

    public Section createSection(Section section) throws ModelException {
        return sectionDAO.create(section.getTitle());
    }

    public Section updateSection(Section section) throws ModelException {
        return sectionDAO.update(section.getId(), section.getTitle());
    }

    public void deleteSection(int id) throws ModelException {
        sectionDAO.delete(id);
    }

    public Dish createDish(Dish dish) throws ModelException {

        return dishDAO.create(dish.getSectionID(), dish.getTitle(), dish.getIcon(), dish.getPrice(), dish.getDescription());
    }

    public Dish updateDish(Dish dish) throws ModelException {

        return dishDAO.update(dish.getId(), dish.getSectionID(), dish.getTitle(), dish.getIcon(), dish.getPrice(), dish.getDescription());
    }

    public Dish updateDishIcon(int dishID, String icon) throws ModelException {
        Dish dish = dishDAO.findByID(dishID);
        return dishDAO.update(dish.getId(), dish.getSectionID(), dish.getTitle(), icon, dish.getPrice(), dish.getDescription());
    }

    public void deleteDish(int id) throws ModelException {
        dishDAO.delete(id);
    }

    public Map<Section, List<Dish>> getMenu() throws ModelException {

        Map<Section, List<Dish>> menu = new HashMap<Section, List<Dish>>();

        List<Section> sections = sectionDAO.findAll();
        for (Section section : sections) {

            List<Dish> dishs = dishDAO.findBySectionID(section.getId());
            menu.put(section, dishs);
        }

        return menu;
    }

}
