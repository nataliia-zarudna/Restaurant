package sirobaba.testtask.restaurant.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import sirobaba.testtask.restaurant.model.service.MenuService;
import sirobaba.testtask.restaurant.model.ModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sirobaba.testtask.restaurant.model.Roles;
import sirobaba.testtask.restaurant.model.entity.Section;

import javax.validation.Valid;
import java.util.logging.Logger;

/**
 * Created by Nataliia on 27.07.2015.
 */
@Controller
public class SectionController {

    public static final Logger log = Logger.getLogger(SectionController.class.getName());

    @Autowired
    private MenuService menuService;
    @Autowired
    private ErrorHandler errorHandler;

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/addSection", method = RequestMethod.POST)
    public String addSection(@Valid @ModelAttribute(value = "section") Section section
            , BindingResult bindingResult
            , ModelMap model) {

        try {
            if (!bindingResult.hasErrors()) {
                menuService.createSection(section);

            } else {
                model.addAttribute("createError", "true");
                return PageNames.EDIT_MENU;
            }

        } catch (ModelException e) {
            return errorHandler.handle(model, log, e);
        }

        return "redirect:" + PageNames.EDIT_MENU;
    }

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/updateSection", method = RequestMethod.POST)
    public String updateSection(@Valid @ModelAttribute(value = "section") Section section
                                , BindingResult bindingResult
            , ModelMap modelMap) {

        try {

            if(!bindingResult.hasErrors()) {
                menuService.updateSection(section);

            } else {
                modelMap.addAttribute("createError", "true");
                return PageNames.EDIT_MENU;
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.EDIT_MENU;
    }

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/deleteSection", method = RequestMethod.GET)
    public String deleteSection(@RequestParam(value = "id") int id, ModelMap modelMap) {

        try {

            menuService.deleteSection(id);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.EDIT_MENU;
    }

}
