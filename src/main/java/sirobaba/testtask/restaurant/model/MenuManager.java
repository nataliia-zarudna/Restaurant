package sirobaba.testtask.restaurant.model;

import sirobaba.testtask.restaurant.model.dish.Dish;
import sirobaba.testtask.restaurant.model.dish.DishDAO;
import sirobaba.testtask.restaurant.model.section.Section;
import sirobaba.testtask.restaurant.model.section.SectionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nataliia on 28.07.2015.
 */
@Service("menuManager")
public class MenuManager {

    @Autowired
    private SectionDAO sectionDAO;
    @Autowired
    private DishDAO dishDAO;

    public Section createSection(String title) throws ModelException {

        //if (title != null && !title.isEmpty()) {

            return sectionDAO.create(title);

        /*} else {
            throw new ModelException("Section's title can not be empty");
        }*/
    }

    public Section updateSection(int id, String title) throws ModelException {
        return sectionDAO.update(id, title);
    }

    public void deleteSection(int id) throws ModelException {
        sectionDAO.delete(id);
    }

    public Dish createDish(int sectionID, String title, String icon, double price, String description) throws ModelException {

        return dishDAO.create(sectionID, title, icon, price, description);
    }

    public Dish updateDish(int id, int sectionID, String title, String icon, double price, String description) throws ModelException {

        return dishDAO.update(id, sectionID, title, icon, price, description);
    }

    public void deleteDish(int id) throws ModelException {
        dishDAO.delete(id);
    }

    public Map<Section, List<Dish>> getMenu() throws ModelException {

        Map<Section, List<Dish>> menu = new HashMap<Section, List<Dish>>();

        List<Section> sections = sectionDAO.findAll();
        for(Section section : sections) {

            List<Dish> dishs = dishDAO.findBySectionID(section.getId());
            menu.put(section, dishs);
        }

        return menu;
    }

}
