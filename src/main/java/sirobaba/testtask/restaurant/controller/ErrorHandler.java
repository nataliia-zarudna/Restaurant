package sirobaba.testtask.restaurant.controller;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sirobaban on 28.07.2015.
 */
@Service("errorHandler")
public class ErrorHandler {

    private static final String DEFAULT_ERROR_PAGE = "error";

    public String handle(ModelMap modelMap, Logger log, Throwable e) {
        return handle(modelMap, log, e, e.getMessage());
    }

    public String handle(ModelMap modelMap, Logger log, Throwable e, String message) {

        log.log(Level.SEVERE, message, e);
        modelMap.addAttribute("errorMessage", message);
        return "redirect:" + DEFAULT_ERROR_PAGE;
    }

}
