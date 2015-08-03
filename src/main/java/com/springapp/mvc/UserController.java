package com.springapp.mvc;

import model.ModelException;
import model.UserManager;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Nataliia on 29.07.2015.
 */
@Controller
public class UserController {

    public static final Logger log = Logger.getLogger(SectionController.class.getName());

    @Autowired
    private UserManager userManager;
    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private ControllerHelper controllerHelper;

    @RequestMapping(value = "/register_old", method = RequestMethod.POST)
    public String registerOld(@RequestParam(value = "firstName") String firstName
            , @RequestParam(value = "lastName") String lastName
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "phone") String phone
            , @RequestParam(value = "email") String email
            , ModelMap model) {

        try {

            boolean isAdmin = false;
            userManager.createUser(firstName, lastName, password, phone, email, isAdmin);

        } catch (ModelException e) {
            return errorHandler.handle(model, log, e);
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user
            , BindingResult result
            , ModelMap modelMap) {

        try {

            boolean isAdmin = false;
            User createdUser = userManager.createUser(user.getFirstName(), user.getLastName(), user.getPassword()
                    , user.getPhone(), user.getEmail(), isAdmin);

            modelMap.addAttribute("user", createdUser);

        } catch (ModelException e) {
            //return errorHandler.handle(model, log, e);
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@RequestParam(value = "id") int id
            , @RequestParam(value = "firstName") String firstName
            , @RequestParam(value = "lastName") String lastName
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "phone") String phone
            , @RequestParam(value = "email") String email
            , @RequestParam(value = "isAdmin") boolean isAdmin
            , ModelMap modelMap) {

        try {

            userManager.updateUser(id, firstName, lastName, password, phone, email, isAdmin);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(@RequestParam(value = "id") int id, ModelMap modelMap) {

        try {

            userManager.deleteUser(id);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:usersAdmin";
    }

    @RequestMapping(value = "/usersAdmin", method = RequestMethod.GET)
    public String getAllUsers(ModelMap modelMap) {

        try {

            UserDetails user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<User> users = userManager.getAllUsers();
                modelMap.addAttribute("users", users);

            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "usersAdmin";
    }

  /*  @ModelAttribute("user")
    public User getUser() {

        return new User();
    }

    @RequestMapping(method = RequestMethod.GET)
    @InitBinder("user")
    public void get() {

        //return "adduserform";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@Valid User user, Errors errors) {

        if (errors.hasErrors()) {
            return "adduserform";
        } else {
            return "userinfoview";
        }
    }*/
}
