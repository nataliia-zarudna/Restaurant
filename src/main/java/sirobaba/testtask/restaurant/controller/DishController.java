package sirobaba.testtask.restaurant.controller;

import org.springframework.security.access.annotation.Secured;
import sirobaba.testtask.restaurant.model.*;
import sirobaba.testtask.restaurant.model.entity.Dish;
import sirobaba.testtask.restaurant.model.entity.Order;
import sirobaba.testtask.restaurant.model.entity.Section;
import sirobaba.testtask.restaurant.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sirobaba.testtask.restaurant.model.service.MenuService;
import sirobaba.testtask.restaurant.model.service.OrderService;
import sirobaba.testtask.restaurant.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
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
    private MenuService menuService;
    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ControllerHelper controllerHelper;

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(ModelMap modelMap, HttpServletRequest request) {

        try {

            Map<Section, List<Dish>> menu = menuService.getMenu();
            modelMap.addAttribute("menu", menu);

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<Order> availableOrders = orderService.getAllOrdersByUser(user.getId());
                modelMap.addAttribute("availableOrders", availableOrders);
            }

            modelMap.addAttribute("user", new User());

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
        }


        return PageNames.MENU;
    }

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/editMenu", method = RequestMethod.GET)
    public String editMenu(ModelMap modelMap, HttpServletRequest request) {

        try {

            Map<Section, List<Dish>> menu = menuService.getMenu();
            modelMap.addAttribute("menu", menu);

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
        }


        return PageNames.EDIT_MENU;
    }

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value="/addDish", method=RequestMethod.POST)
    public String addDish(@RequestParam(value="sectionID") int sectionID
            , @RequestParam(value="title") String title
            , @RequestParam(value="icon") String icon
            , @RequestParam(value="price") int price
            , @RequestParam(value="description") String description
            , ModelMap modelMap) {

        try {

            menuService.createDish(sectionID, title, icon, price, description);

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.EDIT_MENU;
    }

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value="/updateDish", method=RequestMethod.POST)
    public String updateDish(@RequestParam(value="id") int id
            , @RequestParam(value="sectionID") int sectionID
            , @RequestParam(value="title") String title
            , @RequestParam(value="icon") String icon
            , @RequestParam(value="price") double price
            , @RequestParam(value="description") String description
            , ModelMap modelMap) {

        try {

            menuService.updateDish(id, sectionID, title, icon, price, description);

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.EDIT_MENU;
    }

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value="/deleteDish", method=RequestMethod.GET)
    public String deleteDish(@RequestParam(value="id") int id, ModelMap modelMap) {

        try {

            menuService.deleteDish(id);

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.EDIT_MENU;
    }


}
