package com.springapp.mvc;

import model.MenuManager;
import model.ModelException;
import model.OrderManager;
import model.UserManager;
import model.dish.Dish;
import model.dish.DishDAO;
import model.group.Group;
import model.order.Order;
import model.section.Section;
import model.section.SectionDAO;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Nataliia on 27.07.2015.
 */
@Controller
public class DishController {

    private static final Logger log = Logger.getLogger(DishController.class.getName());

    @Autowired
    private MenuManager menuManager;
    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private UserManager userManager;
    @Autowired
    private OrderManager orderManager;
    @Autowired
    private ControllerHelper controllerHelper;

    @RequestMapping(value = {"/menu", "/edit_menu"}, method = RequestMethod.GET)
    public String getMenu(ModelMap modelMap, HttpServletRequest request) {

        try {

            Map<Section, List<Dish>> menu = menuManager.getMenu();
            modelMap.addAttribute("menu", menu);

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<Order> availableOrders = orderManager.getAllOrdersByUser(user.getId());
                modelMap.addAttribute("availableOrders", availableOrders);
            }

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
        }


        return request.getRequestURI();
    }

    @RequestMapping(value="/addDish", method=RequestMethod.POST)
    public String addDish(@RequestParam(value="sectionID") int sectionID
            , @RequestParam(value="title") String title
            , @RequestParam(value="icon") String icon
            , @RequestParam(value="price") int price
            , @RequestParam(value="description") String description
            , ModelMap modelMap) {

        try {

            menuManager.createDish(sectionID, title, icon, price, description);

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
        }

        return "redirect:edit_menu";
    }

    @RequestMapping(value="/updateDish", method=RequestMethod.POST)
    public String updateDish(@RequestParam(value="id") int id
            , @RequestParam(value="sectionID") int sectionID
            , @RequestParam(value="title") String title
            , @RequestParam(value="icon") String icon
            , @RequestParam(value="price") double price
            , @RequestParam(value="description") String description
            , ModelMap modelMap) {

        try {

            menuManager.updateDish(id, sectionID, title, icon, price, description);

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
        }

        return "redirect:edit_menu";
    }

    @RequestMapping(value="/deleteDish", method=RequestMethod.GET)
    public String updateDish(@RequestParam(value="id") int id, ModelMap modelMap) {

        try {

            menuManager.deleteDish(id);

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
        }

        return "redirect:edit_menu";
    }


}
