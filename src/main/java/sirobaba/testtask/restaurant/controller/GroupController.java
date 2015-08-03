package sirobaba.testtask.restaurant.controller;

import sirobaba.testtask.restaurant.model.GroupDetails;
import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.UserManager;
import sirobaba.testtask.restaurant.model.group.Group;
import sirobaba.testtask.restaurant.model.user.User;
import sirobaba.testtask.restaurant.model.userrequest.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Nataliia on 29.07.2015.
 */
@Controller
public class GroupController {

    public static final Logger log = Logger.getLogger(SectionController.class.getName());

    @Autowired
    private UserManager userManager;
    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private ControllerHelper controllerHelper;

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String getUserGroups(ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<GroupDetails> userGroups = userManager.getUserGroupDetails(user.getId());
                modelMap.addAttribute("userGroups", userGroups);

                List<Group> allGroups = userManager.getNotUserGroups(user.getId());
                modelMap.addAttribute("allGroups", allGroups);

                List<Group> requestedGroups = userManager.getRequestedGroups(user.getId());
                modelMap.addAttribute("requestedGroups", requestedGroups);

                List<UserRequest> userRequestsToAccept = userManager.getUserRequestsToAccept(user.getId());
                modelMap.addAttribute("requestsCount", userRequestsToAccept.size());

            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "groups";
    }

    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    public String addGroup(@RequestParam(value = "title") String title
            , ModelMap model) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                userManager.createGroup(title, user.getId());
            }

        } catch (ModelException e) {
            return errorHandler.handle(model, log, e);
        }

        return "redirect:groups";
    }

    @RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
    public String updateGroup(@RequestParam(value = "id") int id
            , @RequestParam(value = "title") String title
            , @RequestParam(value = "ownerID") int ownerID
            , ModelMap modelMap) {

        try {

            userManager.updateGroup(id, title, ownerID);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    @RequestMapping(value = "/deleteGroup", method = RequestMethod.GET)
    public String deleteGroup(@RequestParam(value = "id") int id
            , ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                userManager.deleteGroup(id, user.getId());
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    @RequestMapping(value = "/joinGroup", method = RequestMethod.GET)
    public String joinGroup(@RequestParam(value = "id") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            if (user != null) {
                userManager.createUserRequest(user.getId(), groupID);
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    @RequestMapping(value = "/removeRequest", method = RequestMethod.GET)
    public String removeRequest(@RequestParam(value = "groupID") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            if (user != null) {
                userManager.removeUserRequest(user.getId(), groupID, user.getId());
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    @RequestMapping(value = "/declineRequest", method = RequestMethod.GET)
    public String declineRequest(@RequestParam(value = "userID") int userID
            , @RequestParam(value = "groupID") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            if (user != null) {
                userManager.removeUserRequest(userID, groupID, user.getId());
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    @RequestMapping(value = "/acceptRequest", method = RequestMethod.GET)
    public String acceptRequest(@RequestParam(value = "userID") int userID
            , @RequestParam(value = "groupID") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            if (user != null) {
                userManager.acceptUserRequest(userID, groupID, user.getId());
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    @RequestMapping(value = "/leaveGroup", method = RequestMethod.GET)
    public String leaveGroup(@RequestParam(value = "groupID") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            if (user != null) {
                userManager.removeUserFromGroup(user.getId(), groupID, user.getId());
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    @RequestMapping(value = "/groupsAdmin", method = RequestMethod.GET)
    public String getAllGroups(ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<GroupDetails> userGroups = userManager.getAllGroupDetails();
                modelMap.addAttribute("groups", userGroups);

            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "groupsAdmin";
    }
}
