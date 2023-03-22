package com.example.top.controller;

import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.enums.PaymentStatus;
import com.example.top.enums.ServiceStatus;
import com.example.top.service.DimensionsService;
import com.example.top.service.EmployeeService;
import com.example.top.service.OrderService;
import com.example.top.service.ServiceTypeService;
import com.example.top.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ServiceTypeService serviceTypeService;
    @Autowired
    private DimensionsService dimensionsService;

    @GetMapping({"/add-order", "/edit-order"})
    public ModelAndView renderOrder(Long id) {
        var order = (id == null) ? new Order() : orderService.getOrder(id);

        var mv = new ModelAndView();
        mv.addObject("order", order);
        mv.addObject("employees", employeeService.findAllEmployees());
        mv.addObject("serviceTypes", serviceTypeService.findAllServiceTypes());
        mv.addObject("dimensions", dimensionsService.findAllDimensions());
        mv.addObject("serviceStatus", Arrays.stream(ServiceStatus.values()).toList());
        mv.addObject("paymentStatus", Arrays.stream(PaymentStatus.values()).toList());
        mv.addObject("orderStatus", Arrays.stream(OrderStatus.values()).toList());
        mv.setViewName("order/save-order");

        return mv;
    }

    @PostMapping({"/save-order", "/update-order"})
    public RedirectView saveOrder(Order order) {
        orderService.saveOrder(order);

        if (order.getOrderId() != null) return new RedirectView("orders");

        return new RedirectView("add-order");
    }

    @GetMapping("/delete-order")
    public RedirectView deleteOrder(Long id) {
        orderService.deleteOrder(id);

        return new RedirectView("orders");
    }

    @GetMapping({"/", "/orders"})
    public ModelAndView getOrders(String search, String orderStatus) {
        var mv = new ModelAndView();
        mv.addObject("orders", getOrdersByParams(search, orderStatus));
        mv.setViewName("order/order");

        return mv;
    }

    public List<Order> getOrdersByParams(String search, String orderStatus) {
        List<Order> orders;
        if (GeneralUtil.isQualifiedString(orderStatus)) orders = getOrdersBySearchAndOrderStatus(search, orderStatus);
        else if (!GeneralUtil.isQualifiedString(search)) orders = orderService.findOrdersByOrderStatus("Pending");
        else orders = orderService.findOrdersByCustomerNameContaining(search);

        return orders;
    }

    public List<Order> getOrdersBySearchAndOrderStatus(String search, String orderStatus) {
        List<Order> orders;
        if (!GeneralUtil.isQualifiedString(search)) orders = orderService.findOrdersByOrderStatus(orderStatus);
        else orders = orderService.findOrdersByOrderStatusAndCustomerNameContaining(orderStatus, search);

        return orders;
    }
}
