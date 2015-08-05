package sirobaba.testtask.restaurant.controller;

import sirobaba.testtask.restaurant.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Nataliia on 31.07.2015.
 */
@Service("authenticatedUserProvider")
public class AuthenticatedUserProvider {

    public static final Logger log = Logger.getLogger(AuthenticatedUserProvider.class.getName());

    public User getCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal != null && principal instanceof User) {

            return (User) principal;

        } else {
            log.log(Level.WARNING, "There is no user in current context");
            return null;
        }
    }
}
