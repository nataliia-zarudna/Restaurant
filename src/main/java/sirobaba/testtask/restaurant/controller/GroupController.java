package sirobaba.testtask.restaurant.controller;

import org.springframework.security.access.annotation.Secured;
import sirobaba.testtask.restaurant.model.GroupDetails;
import sirobaba.testtask.restaurant.model.ModelException;
import sirobaba.testtask.restaurant.model.Roles;
import sirobaba.testtask.restaurant.model.UserService;
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
    private UserService userService;
    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private ControllerHelper controllerHelper;

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String userGroups(ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();

            List<GroupDetails> userGroups = userService.getUserGroupDetails(user.getId());
            modelMap.addAttribute("userGroups", userGroups);

            List<Group> allGroups = userService.getNotUserGroups(user.getId());
            modelMap.addAttribute("allGroups", allGroups);

            List<Group> requestedGroups = userService.getRequestedGroups(user.getId());
            modelMap.addAttribute("requestedGroups", requestedGroups);

            List<UserRequest> userRequestsToAccept = userService.getUserRequestsToAccept(user.getId());
            modelMap.addAttribute("requestsCount", userRequestsToAccept.size());


        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return PageNames.GROUPS;
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    public String addGroup(@RequestParam(value = "title") String title
            , ModelMap model) {

        try {

            User user = controllerHelper.getCurrentUser();
            userService.createGroup(title, user.getId());

        } catch (ModelException e) {
            return errorHandler.handle(model, log, e);
        }

        return "redirect:" + PageNames.GROUPS;
    }

    //@PreAuthorize("hasPermission(#title, 'ownerID')")
    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/updateGroup", method = RequestMethod.GET)
    public String updateGroup(@RequestParam(value = "id") int id
            , @RequestParam(value = "title") String title
            , @RequestParam(value = "ownerID") int ownerID
            , ModelMap modelMap) {

        try {

            userService.updateGroup(id, title, ownerID);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    //@PreAuthorize("hasPermission(#title, 'ownerID')")
    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/deleteGroup", method = RequestMethod.GET)
    public String deleteGroup(@RequestParam(value = "id") int id
            , ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                userService.deleteGroup(id, user.getId());
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/joinGroup", method = RequestMethod.GET)
    public String joinGroup(@RequestParam(value = "id") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            if (user != null) {
                userService.createUserRequest(user.getId(), groupID);
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    //@PreAuthorize("hasPermission(#title, 'ownerID')")
    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/removeRequest", method = RequestMethod.GET)
    public String removeRequest(@RequestParam(value = "groupID") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            if (user != null) {
                userService.removeUserRequest(user.getId(), groupID, user.getId());
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    //@PreAuthorize("hasPermission(#title, 'ownerID')")
    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/declineRequest", method = RequestMethod.GET)
    public String declineRequest(@RequestParam(value = "userID") int userID
            , @RequestParam(value = "groupID") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            if (user != null) {
                userService.removeUserRequest(userID, groupID, user.getId());
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    //@PreAuthorize("hasPermission(#title, 'ownerID')")
    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/acceptRequest", method = RequestMethod.GET)
    public String acceptRequest(@RequestParam(value = "userID") int userID
            , @RequestParam(value = "groupID") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            if (user != null) {
                userService.acceptUserRequest(userID, groupID, user.getId());
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    //@PreAuthorize("hasPermission(#title, 'ownerID')")
    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/leaveGroup", method = RequestMethod.GET)
    public String leaveGroup(@RequestParam(value = "groupID") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            if (user != null) {
                userService.removeUserFromGroup(user.getId(), groupID, user.getId());
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/groupsAdmin", method = RequestMethod.GET)
    public String getAllGroups(ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<GroupDetails> userGroups = userService.getAllGroupDetails();
                modelMap.addAttribute("groups", userGroups);

            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "groupsAdmin";
    }
}
