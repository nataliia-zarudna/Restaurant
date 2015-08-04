package sirobaba.testtask.restaurant.controller;

import org.springframework.security.access.annotation.Secured;
import sirobaba.testtask.restaurant.model.MenuManager;
import sirobaba.testtask.restaurant.model.ModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sirobaba.testtask.restaurant.model.Roles;

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

    @Secured(Roles.ROLE_ADMIN)
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

    @Secured(Roles.ROLE_ADMIN)
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

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/deleteSection", method = RequestMethod.GET)
    public String deleteSection(@RequestParam(value = "id") int id, ModelMap modelMap) {

        try {

            menuManager.deleteSection(id);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:edit_menu";
    }

}
