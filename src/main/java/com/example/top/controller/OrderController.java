package com.example.top.controller;

import com.example.top.entity.order.Order;
import com.example.top.enums.ServiceStatus;
import com.example.top.service.DimensionsService;
import com.example.top.service.EmployeeService;
import com.example.top.service.OrderService;
import com.example.top.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        mv.setViewName("order/save-order");

        return mv;
    }

    @PostMapping({"/save-order", "/update-order"})
    public RedirectView saveOrder(Order order) {
        orderService.saveOrder(order);

        if (order.getOrderId() != null) return new RedirectView("orders");

        return new RedirectView("add-order");
    }

    @RequestMapping("/orders")
    public ModelAndView getOrders() {
        var mv = new ModelAndView();
        mv.addObject("orders", orderService.findAllOrders());
        mv.setViewName("order/order");

        return mv;
    }

    @GetMapping("/delete-order")
    public RedirectView deleteOrder(Long id) {
        orderService.deleteOrder(id);

        return new RedirectView("orders");
    }

    @GetMapping("/orders")
    public ModelAndView searchOrders(String search) {
        if (search == null || search.isEmpty() || search.isBlank()) return getOrders();

        var mv = new ModelAndView();
        mv.addObject("orders", orderService.findOrdersByCustomerNameContaining(search));
        mv.setViewName("order/order");

        return mv;
    }
}
