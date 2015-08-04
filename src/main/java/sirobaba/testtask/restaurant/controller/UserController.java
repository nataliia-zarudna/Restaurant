package sirobaba.testtask.restaurant.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.Roles;
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

    public static final String JSP_SUFFIX = ".jsp";

    @Autowired
    private UserService userService;
    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ControllerHelper controllerHelper;

    @Secured(Roles.ROLE_ANONYMOUS)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam(value = "returnURL", required = false, defaultValue = PageNames.INDEX) String returnURL
            , @Valid @ModelAttribute("user") User user
            , BindingResult result
            , ModelMap modelMap) {

        String returnPage = returnURL.contains(PageNames.INDEX) ? PageNames.INDEX : PageNames.MENU;

        try {

            if (!result.hasErrors()) {

                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);

                User createdUser = userService.createUser(user);
                modelMap.addAttribute("user", createdUser);

            } else {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("createError", "true");
                return returnPage;
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + returnPage;
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUser(@Valid @ModelAttribute("user") User user
            , BindingResult result
            , ModelMap modelMap) {

        try {

            if (!result.hasErrors()) {

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

            } else {
                modelMap.addAttribute("user", user);
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return PageNames.PROFILE;
    }

    @Secured({Roles.ROLE_USER, Roles.ROLE_ADMIN})
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(@RequestParam(value = "id") int id, ModelMap modelMap) {

        try {

            userService.deleteUser(id);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:usersAdmin";
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(ModelMap modelMap) {
        return PageNames.PROFILE;
    }

    @Secured(Roles.ROLE_ADMIN)
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

}
