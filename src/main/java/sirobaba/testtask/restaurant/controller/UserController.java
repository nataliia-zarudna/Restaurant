package sirobaba.testtask.restaurant.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.UserService;
import sirobaba.testtask.restaurant.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Nataliia on 29.07.2015.
 */
@Controller
public class UserController {

    public static final Logger log = Logger.getLogger(SectionController.class.getName());

    @Autowired
    private UserService userService;
    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ControllerHelper controllerHelper;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("user") User user
            , BindingResult result
            , ModelMap modelMap) {

        try {

            if (!result.hasErrors()) {

                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);

                User createdUser = userService.createUser(user);
                modelMap.addAttribute("user", createdUser);

            } else {
                return "redirect:" + PageNames.INDEX;
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.INDEX;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute(value = "user") User user
            , ModelMap modelMap) {

        try {

            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userService.updateUser(user);

            User principal = controllerHelper.getCurrentUser();

            principal.setFirstName(user.getFirstName());
            principal.setLastName(user.getLastName());
            principal.setPassword(encodedPassword);
            principal.setPhone(user.getPhone());
            principal.setEmail(user.getEmail());
            principal.setIsAdmin(user.getIsAdmin());


        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.PROFILE;
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(@RequestParam(value = "id") int id, ModelMap modelMap) {

        try {

            userService.deleteUser(id);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:usersAdmin";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(ModelMap modelMap) {

        User user = controllerHelper.getCurrentUser();
        modelMap.addAttribute("user", user);

        return PageNames.PROFILE;
    }

    @RequestMapping(value = "/usersAdmin", method = RequestMethod.GET)
    public String getAllUsers(ModelMap modelMap) {

        try {

            UserDetails user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<User> users = userService.getAllUsers();
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
