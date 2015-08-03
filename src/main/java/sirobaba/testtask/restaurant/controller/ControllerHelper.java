package sirobaba.testtask.restaurant.controller;

import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.UserManager;
import sirobaba.testtask.restaurant.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Nataliia on 31.07.2015.
 */
@Service("controllerHelper")
public class ControllerHelper {

    public static final Logger log = Logger.getLogger(ControllerHelper.class.getName());

    @Autowired
    private UserManager userManager;

    public User getCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal != null && principal instanceof User) {

            return (User) principal;

        } else {
            log.log(Level.WARNING, "There is no user in current context");

            //TODO: change to return null;
            try {
                return userManager.getUser(1);//loadUserByUsername("ivan@gmail.com");
            } catch (ModelException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
