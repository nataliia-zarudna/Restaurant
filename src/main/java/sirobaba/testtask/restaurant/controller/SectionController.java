package sirobaba.testtask.restaurant.controller;

import sirobaba.testtask.restaurant.model.MenuManager;
import sirobaba.testtask.restaurant.model.ModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

/**
 * Created by Nataliia on 27.07.2015.
 */
@Controller
public class SectionController {

    public static final Logger log = Logger.getLogger(SectionController.class.getName());

    @Autowired
    private MenuManager menuManager;
    @Autowired
    private ErrorHandler errorHandler;

    @RequestMapping(value = "/addSection", method = RequestMethod.POST)
    public String addSection(@RequestParam(value = "title") String title
            , ModelMap model) {

        try {

            menuManager.createSection(title);

        } catch (ModelException e) {
            return errorHandler.handle(model, log, e);
        }

        return "redirect:edit_menu";
    }

    @RequestMapping(value = "/updateSection", method = RequestMethod.POST)
    public String updateSection(@RequestParam(value = "id") int id
            , @RequestParam(value = "title") String title
            , ModelMap modelMap) {

        try {

            menuManager.updateSection(id, title);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:edit_menu";
    }

    @RequestMapping(value = "/deleteSection", method = RequestMethod.GET)
    public String deleteSection(@RequestParam(value = "id") int id, ModelMap modelMap) {

        try {

            menuManager.deleteSection(id);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:edit_menu";
    }

  /*  @RequestMapping(value = "/sections", method = RequestMethod.GET)
    public String getSections(ModelMap modelMap) {

        try {

            List<Section> sections = sectionDAO.findAll();
            modelMap.addAttribute("sections", sections);

        } catch (ModelException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            modelMap.addAttribute("errorMessage", e.getMessage());
            return "error";
        }

        return "sections";
    }

    @RequestMapping(value = "/section", method = RequestMethod.GET)
    public String getSection(@RequestParam(value = "id") String id, ModelMap modelMap) {

        try {

            Section section = sectionDAO.findByID(Integer.valueOf(id));
            modelMap.addAttribute("section", section);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }
        
        return "section";
    }
*/
}
