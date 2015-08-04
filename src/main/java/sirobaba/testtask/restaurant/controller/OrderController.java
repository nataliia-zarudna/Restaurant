package sirobaba.testtask.restaurant.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import sirobaba.testtask.restaurant.controller.viewentity.GroupOrderDetails;
import sirobaba.testtask.restaurant.controller.viewentity.OrderDetails;
import sirobaba.testtask.restaurant.model.*;
import sirobaba.testtask.restaurant.model.entity.Dish;
import sirobaba.testtask.restaurant.model.entity.Group;
import sirobaba.testtask.restaurant.model.entity.Order;
import sirobaba.testtask.restaurant.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import sirobaba.testtask.restaurant.model.service.GroupService;
import sirobaba.testtask.restaurant.model.service.OrderService;
import sirobaba.testtask.restaurant.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Nataliia on 29.07.2015.
 */
@Controller
public class OrderController {

    public static final Logger log = Logger.getLogger(SectionController.class.getName());

    public static final String ORDER_DETAILS_ATTR_NAME = "currentOrderDetails";

    @Autowired
    private OrderService orderService;
    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private ControllerHelper controllerHelper;

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/addUserOrder", method = RequestMethod.POST)
    public String addUserOrder(@RequestParam(value = "title") String title
            , ModelMap model) {

        try {

            User user = controllerHelper.getCurrentUser();
            Order createdOrder = orderService.createUserOrder(title, user.getId(), new Date());
            model.addAttribute("id", createdOrder.getId());

        } catch (ModelException e) {
            return errorHandler.handle(model, log, e);
        }

        return "redirect:order";
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/addGroupOrder", method = RequestMethod.POST)
    public String addGroupOrder(@RequestParam(value = "groupID") int groupID
            , @Valid @ModelAttribute(value = "order") Order order
            , ModelMap model) {

        try {

            Order createdOrder = orderService.createGroupOrder(order, groupID);
            model.addAttribute("id", createdOrder.getId());

        } catch (ModelException e) {
            return errorHandler.handle(model, log, e);
        }

        return "redirect:groupOrder";
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/orderDish", method = RequestMethod.GET)
    public String addDishToCurrentOrder(@RequestParam(value = "dishID") int dishID
            , ModelMap modelMap, HttpServletRequest request) {

        try {

            User user = controllerHelper.getCurrentUser();

            OrderDetails orderDetails = (OrderDetails) request.getSession().getAttribute(ORDER_DETAILS_ATTR_NAME);
            Order order = null;
            if (orderDetails == null) {

                order = orderService.createUserOrder(user.getId(), new Date());

            } else {
                order = orderDetails.getOrder();
            }

            orderService.addDishToOrder(order.getId(), user.getId(), dishID);

            addOrderDetailsToSession(order.getId(), request);


        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:menu";
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/myOrders", method = RequestMethod.GET)
    public String getMyOrders(ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();

            List<OrderDetails> orderDetails = new ArrayList<OrderDetails>();

            List<Order> orders = orderService.getUserOrders(user.getId());
            for (Order order : orders) {
                orderDetails.add(getOrderDetails(order));
            }

            modelMap.addAttribute("orderDetailses", orderDetails);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return PageNames.MY_ORDERS;

    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/groupOrders", method = RequestMethod.GET)
    public String getGroupOrders(ModelMap modelMap) {

        User user = controllerHelper.getCurrentUser();

        List<Group> userGroups = groupService.getUserGroups(user.getId());
        modelMap.addAttribute("userGroups", userGroups);


        return PageNames.GROUP_ORDERS;
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/userGroupOrderDetailsByUsers", method = RequestMethod.GET)
    public
    @ResponseBody
    List<GroupOrderDetails> userGroupOrderDetailsByUsers(ModelMap modelMap) {
        User user = controllerHelper.getCurrentUser();
        List<Group> groups = groupService.getUserGroups(user.getId());
        return getGroupOrderDetailsByUsers(modelMap, groups);
    }

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/allGroupOrderDetailsByUsers", method = RequestMethod.GET)
    public
    @ResponseBody
    List<GroupOrderDetails> allGroupOrderDetailsByUsers(ModelMap modelMap) {

        try {
            List<Group> groups = groupService.getAllGroups();
            return getGroupOrderDetailsByUsers(modelMap, groups);

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
            return null;
        }
    }

    List<GroupOrderDetails> getGroupOrderDetailsByUsers(ModelMap modelMap, List<Group> groups) {

        try {

            List<GroupOrderDetails> groupOrderDetailses = new ArrayList<GroupOrderDetails>();

            for (Group group : groups) {

                List<Order> groupOrders = orderService.getGroupOrders(group.getId());
                for (Order order : groupOrders) {
                    groupOrderDetailses.add(getGroupOrderDetails(order, group));
                }
            }

            return groupOrderDetailses;

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
        }

        return null;
    }


    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/userGroupOrderDetailsByDishes", method = RequestMethod.GET)
    public
    @ResponseBody
    List<OrderDetails> userGroupOrderDetailsByDishes(ModelMap modelMap) {
        User user = controllerHelper.getCurrentUser();
        List<Group> groups = groupService.getUserGroups(user.getId());
        return getGroupOrderDetailsByDishes(modelMap, groups);
    }

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/allGroupOrderDetailsByDishes", method = RequestMethod.GET)
    public
    @ResponseBody
    List<OrderDetails> allGroupOrderDetailsByDishes(ModelMap modelMap) {

        try {
            List<Group> groups = groupService.getAllGroups();
            return getGroupOrderDetailsByDishes(modelMap, groups);

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
            return null;
        }
    }

    List<OrderDetails> getGroupOrderDetailsByDishes(ModelMap modelMap, List<Group> groups) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<OrderDetails> orderDetailses = new ArrayList<OrderDetails>();

                for (Group group : groups) {

                    List<Order> groupOrders = orderService.getGroupOrders(group.getId());
                    for (Order order : groupOrders) {
                        orderDetailses.add(getOrderDetails(order));
                    }
                }

                return orderDetailses;
            }

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
        }

        return null;
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/startOrdering", method = RequestMethod.GET)
    public String addDishToOrder(@RequestParam(value = "orderID") int orderID
            , ModelMap modelMap
            , HttpServletRequest request) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                addOrderDetailsToSession(orderID, request);
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.MENU;
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/addDishToOrder", method = RequestMethod.GET)
    public String addDishToOrder(@RequestParam(value = "orderID") int orderID
            , @RequestParam(value = "dishID") int dishID
            , ModelMap modelMap
            , HttpServletRequest request) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                orderService.addDishToOrder(orderID, user.getId(), dishID);

                addOrderDetailsToSession(orderID, request);
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.MENU;
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    public String updateSection(@RequestParam(value = "id") int id
            , @RequestParam(value = "title") String title
            , @RequestParam(value = "reservationTime") String reservationTimeStr
            , @RequestParam(value = "reservationTimePattern") String timePattern
            , ModelMap modelMap) {

        try {

            SimpleDateFormat format = new SimpleDateFormat(timePattern);
            Date reservationTime = format.parse(reservationTimeStr);

            orderService.updateOrder(id, title, reservationTime);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        } catch (ParseException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:" + PageNames.EDIT_MENU;
    }

    @Secured({Roles.ROLE_USER, Roles.ROLE_ADMIN})
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String showOrder(@RequestParam(value = "id") int id
            , ModelMap modelMap) {

        try {

            String url = PageNames.INDEX;

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                if (!orderService.isGroupOrder(id)) {

                    Order order = orderService.getOrder(id);
                    OrderDetails orderDetails = getOrderDetails(order);
                    modelMap.addAttribute("orderDetails", orderDetails);
                    url = PageNames.ORDER;

                } else {

                    Order order = orderService.getOrder(id);
                    GroupOrderDetails groupOrderDetails = getGroupOrderDetails(order);
                    modelMap.addAttribute("orderDetails", groupOrderDetails);
                    url = PageNames.GROUP_ORDER;
                }
            }

            return url;

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }
    }

    @Secured(Roles.ROLE_USER)
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(@RequestParam(value = "orderID") int orderID
            , ModelMap modelMap
            , HttpServletRequest request) {

        try {
            User user = controllerHelper.getCurrentUser();
            String viewURL = "/";
            if (user != null) {

                Order updatedOrder = orderService.checkout(orderID, user.getId());
                request.getSession().setAttribute(ORDER_DETAILS_ATTR_NAME, null);

                viewURL = (updatedOrder.getGroupID() < 0) ? PageNames.MY_ORDERS : PageNames.GROUP_ORDERS;
            }

            return "redirect:" + viewURL;

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }
    }

    @Secured({Roles.ROLE_USER, Roles.ROLE_ADMIN})
    @RequestMapping(value = {"/cancelOrder", "/closeOrder"}, method = RequestMethod.GET)
    public String cancelOrder(@RequestParam(value = "id") int id
            , ModelMap modelMap
            , HttpServletRequest request) {

        try {

            Order order = orderService.getOrder(id);

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                orderService.cancelOrder(id, user.getId());
                removeOrderDetailsFromSession(request);
            }

            String viewURL = (order.getGroupID() < 0) ? PageNames.MY_ORDERS : PageNames.GROUP_ORDERS;
            return "redirect:" + viewURL;

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }
    }

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/ordersAdmin", method = RequestMethod.GET)
    public String getAllUserOrders(ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<OrderDetails> orderDetailses = new ArrayList<OrderDetails>();

                List<Order> orders = orderService.getAllUserOrders();
                for (Order order : orders) {
                    orderDetailses.add(getOrderDetails(order));
                }

                modelMap.addAttribute("orderDetailses", orderDetailses);
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return PageNames.ORDERS_ADMIN;

    }

    @Secured(Roles.ROLE_ADMIN)
    @RequestMapping(value = "/groupOrdersAdmin", method = RequestMethod.GET)
    public String getAllGroupOrders(ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<GroupOrderDetails> groupOrderDetailses = new ArrayList<GroupOrderDetails>();

                List<Order> orders = orderService.getAllGroupOrders();
                for (Order order : orders) {
                    groupOrderDetailses.add(getGroupOrderDetails(order));
                }

                modelMap.addAttribute("groupOrderDetailses", groupOrderDetailses);
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return PageNames.GROUP_ORDERS_ADMIN;
    }

    private OrderDetails getOrderDetails(Order order) throws ModelException {
        String orderStatus = orderService.getOrderStatusStringRepresentation(order.getStatusID());
        List<Dish> dishes = orderService.getOrderedDishes(order.getId());

        return new OrderDetails(order, orderStatus, dishes);
    }

    private GroupOrderDetails getGroupOrderDetails(Order order) throws ModelException {
        Group group = groupService.getGroup(order.getGroupID());
        return getGroupOrderDetails(order, group);
    }

    private GroupOrderDetails getGroupOrderDetails(Order order, Group group) throws ModelException {

        Map<User, List<Dish>> usersOrderedDishes = new HashMap<User, List<Dish>>();

        String orderStatus = orderService.getOrderStatusStringRepresentation(order.getStatusID());
        List<User> groupUsers = userService.getGroupUsers(group.getId());
        for (User groupUser : groupUsers) {

            List<Dish> dishes = orderService.getOrderedDishes(order.getId(), groupUser.getId());
            usersOrderedDishes.put(groupUser, dishes);
        }

        return new GroupOrderDetails(order, orderStatus, usersOrderedDishes);
    }

    private void addOrderDetailsToSession(int orderID, HttpServletRequest request) throws ModelException {

        User user = controllerHelper.getCurrentUser();

        Order order = orderService.getOrder(orderID);
        String orderStatus = orderService.getOrderStatusStringRepresentation(order.getStatusID());
        List<Dish> dishes = orderService.getOrderedDishes(orderID, user.getId());

        OrderDetails orderDetails = new OrderDetails(order, orderStatus, dishes);
        request.getSession().setAttribute(ORDER_DETAILS_ATTR_NAME, orderDetails);

    }

    private void removeOrderDetailsFromSession(HttpServletRequest request) throws ModelException {
        request.getSession().removeAttribute(ORDER_DETAILS_ATTR_NAME);
    }

}
