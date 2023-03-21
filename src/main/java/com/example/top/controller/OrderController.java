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
        if (GeneralUtil.isQualifiedString(orderStatus)) return getOrdersBySearchAndOrderStatus(search, orderStatus);

        if (!GeneralUtil.isQualifiedString(search)) return getOrdersByOrderStatus("Pending");

        var mv = new ModelAndView();
        mv.addObject("orders", orderService.findOrdersByCustomerNameContaining(search));
        mv.setViewName("order/order");

        return mv;
    }

    public ModelAndView getOrdersBySearchAndOrderStatus(String search, String orderStatus) {
        if (!GeneralUtil.isQualifiedString(search)) return getOrdersByOrderStatus(orderStatus);

        var mv = new ModelAndView();
        mv.addObject("orders", orderService.findOrdersByOrderStatusAndCustomerNameContaining(orderStatus, search));
        mv.setViewName("order/order");

        return mv;
    }

    public ModelAndView getOrdersByOrderStatus(String orderStatus) {
        var mv = new ModelAndView();
        mv.addObject("orders", orderService.findOrdersByOrderStatus(orderStatus));
        mv.setViewName("order/order");

        return mv;
    }
}
