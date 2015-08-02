package com.springapp.mvc;

import model.*;
import model.group.Group;
import model.order.Order;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Nataliia on 29.07.2015.
 */
@Controller
public class OrderController {

    public static final Logger log = Logger.getLogger(SectionController.class.getName());

    public static final String ORDER_DETAILS_ATTR_NAME = "currentOrderDetails";

    @Autowired
    private OrderManager orderManager;
    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private UserManager userManager;
    @Autowired
    private ControllerHelper controllerHelper;

    @RequestMapping(value = "/addUserOrder", method = RequestMethod.POST)
    public String addUserOrder(@RequestParam(value = "title") String title
            , ModelMap model) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                Order createdOrder = orderManager.createUserOrder(title, user.getId(), new Date());
                model.addAttribute("id", createdOrder.getId());
            }

        } catch (ModelException e) {
            return errorHandler.handle(model, log, e);
        }

        return "redirect:order";
    }

    @RequestMapping(value = "/addGroupOrder", method = RequestMethod.POST)
    public String addGroupOrder(@RequestParam(value = "title") String title
            , @RequestParam(value = "groupID") int groupID
            , ModelMap model) {

        try {

            Order createdOrder = orderManager.createGroupOrder(title, groupID, new Date());
            model.addAttribute("id", createdOrder.getId());

        } catch (ModelException e) {
            return errorHandler.handle(model, log, e);
        }

        return "redirect:groupOrder";
    }

    @RequestMapping(value = "/orderDish", method = RequestMethod.GET)
    public String addDishToCurrentOrder(@RequestParam(value = "dishID") int dishID
            , ModelMap modelMap, HttpServletRequest request) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                OrderDetails orderDetails = (OrderDetails) request.getSession().getAttribute(ORDER_DETAILS_ATTR_NAME);
                Order order = null;
                if (orderDetails == null) {

                    order = orderManager.createUserOrder(user.getId(), new Date());

                } else {
                    order = orderDetails.getOrder();
                }

                orderManager.addDishToOrder(order.getId(), user.getId(), dishID);
                orderDetails = orderManager.getOrderDetails(order.getId(), user.getId());
                request.getSession().setAttribute(ORDER_DETAILS_ATTR_NAME, orderDetails);
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:menu";
    }

    @RequestMapping(value = "/myOrders", method = RequestMethod.GET)
    public String getMyOrders(ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<OrderDetails> userOrderDetails = orderManager.getUserOrderDetails(user.getId());
                modelMap.addAttribute("userOrderDetails", userOrderDetails);

                /*List<GroupOrderDetails> groupOrderDetailses = orderManager.getGroupOrderDetailsByUser(user.getId());
                modelMap.addAttribute("groupOrderDetailses", groupOrderDetailses);*/

            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "myOrders";

    }

    @RequestMapping(value = "/groupOrders", method = RequestMethod.GET)
    public String getGroupOrders(ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<GroupOrderDetails> groupOrderDetailses = orderManager.getGroupOrderDetailsByUser(user.getId());
                modelMap.addAttribute("groupOrderDetailses", groupOrderDetailses);

                /*List<Group> userGroups = userManager.getUserGroups(user.getId());
                modelMap.addAttribute("userGroups", userGroups);*/

            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "groupOrders";

    }

    @RequestMapping(value = "/groupOrderDetails", method = RequestMethod.GET)
    public
    @ResponseBody
    List<GroupOrderDetails> getGroupOrderDetails(ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<GroupOrderDetails> groupOrderDetailses = orderManager.getGroupOrderDetailsByUser(user.getId());
                //modelMap.addAttribute("groupOrderDetailses", groupOrderDetailses);

                /*List<Group> userGroups = userManager.getUserGroups(user.getId());
                modelMap.addAttribute("userGroups", userGroups);*/

                return groupOrderDetailses;
            }

        } catch (ModelException e) {
            errorHandler.handle(modelMap, log, e);
        }

        return null;
    }

    @RequestMapping(value = "/startOrdering", method = RequestMethod.GET)
    public String addDishToOrder(@RequestParam(value = "orderID") int orderID
            , ModelMap modelMap
            , HttpServletRequest request) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                OrderDetails orderDetails = orderManager.getOrderDetails(orderID, user.getId());
                request.getSession().setAttribute(ORDER_DETAILS_ATTR_NAME, orderDetails);
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:menu";
    }

    @RequestMapping(value = "/addDishToOrder", method = RequestMethod.GET)
    public String addDishToOrder(@RequestParam(value = "orderID") int orderID
            , @RequestParam(value = "dishID") int dishID
            , ModelMap modelMap
            , HttpServletRequest request) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                orderManager.addDishToOrder(orderID, user.getId(), dishID);

                OrderDetails orderDetails = orderManager.getOrderDetails(orderID, user.getId());
                request.getSession().setAttribute(ORDER_DETAILS_ATTR_NAME, orderDetails);
            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:menu";
    }

    @RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
    public String updateSection(@RequestParam(value = "id") int id
            , @RequestParam(value = "title") String title
            , @RequestParam(value = "reservationTime") String reservationTimeStr
            , @RequestParam(value = "reservationTimePattern") String timePattern
            , ModelMap modelMap) {

        try {

            SimpleDateFormat format = new SimpleDateFormat(timePattern);
            Date reservationTime = format.parse(reservationTimeStr);

            orderManager.updateOrder(id, title, reservationTime);

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        } catch (ParseException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "redirect:edit_menu";
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String showOrder(@RequestParam(value = "id") int id
            , ModelMap modelMap) {

        try {

            String url = "/";

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                if (!orderManager.isGroupOrder(id)) {

                    OrderDetails orderDetails = orderManager.getOrderDetails(id, user.getId());
                    modelMap.addAttribute("orderDetails", orderDetails);
                    url = "order";

                } else {

                    GroupOrderDetails groupOrderDetails = orderManager.getGroupOrderDetails(id);
                    modelMap.addAttribute("orderDetails", groupOrderDetails);
                    url = "groupOrder";
                }
            }

            return url;

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(@RequestParam(value = "orderID") int orderID
            , ModelMap modelMap
            , HttpServletRequest request) {

        try {
            User user = controllerHelper.getCurrentUser();
            String viewURL = "/";
            if (user != null) {

                Order updatedOrder = orderManager.checkout(orderID, user.getId());
                request.getSession().setAttribute(ORDER_DETAILS_ATTR_NAME, null);

                viewURL = (updatedOrder.getGroupID() < 0) ? "myOrders" : "groupOrders";
            }

            return "redirect:" + viewURL;

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }
    }

    @RequestMapping(value = {"/cancelOrder", "/closeOrder"}, method = RequestMethod.GET)
    public String cancelOrder(@RequestParam(value = "id") int id
            , ModelMap modelMap
            , HttpServletRequest request) {

        try {

            Order order = orderManager.getOrder(id);

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                orderManager.cancelOrder(id, user.getId());
                request.getSession().setAttribute(ORDER_DETAILS_ATTR_NAME, null);
            }

            String viewURL = (order.getGroupID() < 0) ? "myOrders" : "groupOrders";
            return "redirect:" + viewURL;

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }
    }

    @RequestMapping(value = "/ordersAdmin", method = RequestMethod.GET)
    public String getAllOrders(ModelMap modelMap) {

        try {

            User user = controllerHelper.getCurrentUser();
            if (user != null) {

                List<GroupOrderDetails> groupOrderDetailses = orderManager.getGroupOrderDetailsByUser(user.getId());
                modelMap.addAttribute("groupOrderDetailses", groupOrderDetailses);

                List<Group> userGroups = userManager.getUserGroups(user.getId());
                modelMap.addAttribute("userGroups", userGroups);

            }

        } catch (ModelException e) {
            return errorHandler.handle(modelMap, log, e);
        }

        return "ordersAdmin";

    }

}
