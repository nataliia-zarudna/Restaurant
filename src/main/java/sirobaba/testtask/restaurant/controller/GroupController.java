package sirobaba.testtask.restaurant.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import sirobaba.testtask.restaurant.controller.viewentity.GroupDetails;
import sirobaba.testtask.restaurant.model.*;
import sirobaba.testtask.restaurant.model.entity.Group;
import sirobaba.testtask.restaurant.model.entity.User;
import sirobaba.testtask.restaurant.model.entity.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sirobaba.testtask.restaurant.model.service.GroupService;
import sirobaba.testtask.restaurant.model.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
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
    private GroupService groupService;
    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private ControllerHelper controllerHelper;

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public String userGroups(ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();

            List<GroupDetails> userGroups = getUserGroupDetails(user.getId());
            modelMap.addAttribute("userGroups", userGroups);


            List<Group> allGroups = groupService.getNotUserGroups(user.getId());
            modelMap.addAttribute("allGroups", allGroups);

            List<Group> requestedGroups = groupService.getRequestedGroups(user.getId());
            modelMap.addAttribute("requestedGroups", requestedGroups);

            List<UserRequest> userRequestsToAccept = groupService.getUserRequestsToAccept(user.getId());
            modelMap.addAttribute("requestsCount", userRequestsToAccept.size());


        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return PageNames.GROUPS;
    }

    public List<GroupDetails> getUserGroupDetails(int userID) throws ModelException {

        List<GroupDetails> groupDetailses = new ArrayList<GroupDetails>();

        List<Group> groups = groupService.getUserGroups(userID);
        for (Group group : groups) {

            List<User> groupUsers = userService.getGroupUsers(group.getId());
            User groupOwner = userService.getUser(group.getOwnerID());
            GroupDetails groupDetails = new GroupDetails(group, groupOwner, groupUsers);

            if (group.getOwnerID() == userID) {
                List<UserRequest> userRequests = groupService.getUserRequests(group.getId());
                List<User> usersRequested = new ArrayList<User>();
                for (UserRequest request : userRequests) {
                    User userRequested = userService.getUser(request.getUserID());
                    usersRequested.add(userRequested);
                }
                groupDetails.setUserRequests(usersRequested);
            }

            groupDetailses.add(groupDetails);
        }

        return groupDetailses;
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    public String addGroup(@Valid @ModelAttribute(value = "group") Group group
            , BindingResult bindingResult
            , ModelMap modelMap) {

        try {

            if (!bindingResult.hasErrors()) {
                groupService.createGroup(group);
            } else {
                modelMap.addAttribute("createError", "true");
                return PageNames.GROUPS;
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.GROUPS;
    }

    //@PreAuthorize("hasPermission(#title, 'ownerID')")
    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/updateGroup", method = RequestMethod.GET)
    public String updateGroup(@Valid @ModelAttribute("group") Group group
            , BindingResult bindingResult
            , ModelMap modelMap) {

        try {

            if (!bindingResult.hasErrors()) {
                groupService.updateGroup(group);
            } else {
                modelMap.addAttribute("createError", "true");
                return PageNames.GROUPS;
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:groups";
    }

    //@PreAuthorize("hasPermission(#title, 'ownerID')")
    @Secured({Roles.ROLE_USER, Roles.ROLE_ADMIN})
    @RequestMapping(value = "/deleteGroup", method = RequestMethod.GET)
    public String deleteGroup(@RequestParam(value = "id") int id
            , ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();
            groupService.deleteGroup(id, user.getId());

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.GROUPS;
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/joinGroup", method = RequestMethod.GET)
    public String joinGroup(@RequestParam(value = "id") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            groupService.createUserRequest(user.getId(), groupID);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.GROUPS;
    }

    //@PreAuthorize("hasPermission(#title, 'ownerID')")
    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/removeRequest", method = RequestMethod.GET)
    public String removeRequest(@RequestParam(value = "groupID") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            groupService.removeUserRequest(user.getId(), groupID, user.getId());

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.GROUPS;
    }

    //@PreAuthorize("hasPermission(#title, 'ownerID')")
    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/declineRequest", method = RequestMethod.GET)
    public String declineRequest(@RequestParam(value = "userID") int userID
            , @RequestParam(value = "groupID") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            groupService.removeUserRequest(userID, groupID, user.getId());

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.GROUPS;
    }

    //@PreAuthorize("hasPermission(#title, 'ownerID')")
    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/acceptRequest", method = RequestMethod.GET)
    public String acceptRequest(@RequestParam(value = "userID") int userID
            , @RequestParam(value = "groupID") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            groupService.acceptUserRequest(userID, groupID, user.getId());

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.GROUPS;
    }

    //@PreAuthorize("hasPermission(#title, 'ownerID')")
    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/leaveGroup", method = RequestMethod.GET)
    public String leaveGroup(@RequestParam(value = "groupID") int groupID
            , ModelMap modelMap) {


        try {
            User user = controllerHelper.getCurrentUser();
            groupService.removeUserFromGroup(user.getId(), groupID, user.getId());

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.GROUPS;
    }

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/groupsAdmin", method = RequestMethod.GET)
    public String getAllGroups(ModelMap modelMap) {

        try {

            List<GroupDetails> userGroups = getAllGroupDetails();
            modelMap.addAttribute("groups", userGroups);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return PageNames.GROUPS_ADMIN;
    }

    public List<GroupDetails> getAllGroupDetails() throws ModelException {

        List<GroupDetails> groupDetailses = new ArrayList<GroupDetails>();

        List<Group> groups = groupService.getAllGroups();
        for (Group group : groups) {

            List<User> groupUsers = userService.getGroupUsers(group.getId());
            User groupOwner = userService.getUser(group.getOwnerID());
            GroupDetails groupDetails = new GroupDetails(group, groupOwner, groupUsers);

            groupDetailses.add(groupDetails);
        }

        return groupDetailses;
    }
}
