package com.example.top.controller;

import com.example.top.entity.order.Order;
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

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ServiceTypeService serviceTypeService;

    @GetMapping("/add-order")
    public ModelAndView addOrder() {
        var employees = employeeService.findAllEmployees();
        var serviceTypes = serviceTypeService.findAllServiceTypes();

        var mv = new ModelAndView();
        mv.addObject("order", new Order());
        mv.addObject("employees", employees);
        mv.addObject("serviceTypes", serviceTypes);
        mv.setViewName("order/add-order");

        return mv;
    }

    @PostMapping("/save-order")
    public RedirectView saveOrder(Order order) {
        orderService.saveOrder(order);

        return new RedirectView("add-order");
    }

    @RequestMapping("/orders")
    public ModelAndView getOrders() {
        var mv = new ModelAndView();
        mv.addObject("orders", orderService.findAllOrders());
        mv.setViewName("order/order");

        return mv;
    }

    @GetMapping("/edit-order")
    public ModelAndView editOrder(Long id) {
        var order = orderService.getOrder(id);

        var mv = new ModelAndView();
        mv.addObject("order", order);
        mv.setViewName("order/edit-order");

        return mv;
    }

    @PostMapping("/update-order")
    public RedirectView updateOrder(Order order) {
        orderService.updateOrder(order);

        return new RedirectView("orders");
    }

    @GetMapping("/delete-order")
    public RedirectView deleteOrder(Long id) {
        orderService.deleteOrder(id);

        return new RedirectView("orders");
    }
}
