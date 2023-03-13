package com.example.top.controller;

import com.example.top.service.EmployeeService;
import com.example.top.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private EmployeeService empService;

    @GetMapping("/orders")
    public String showOrders() {
        return "admin-order";
    }

    @GetMapping("/add-order")
    public ModelAndView showAddOrder() {
        var employees = empService.findAllEmployees();

        var mv = new ModelAndView();
        mv.addObject("employees", employees);
        mv.setViewName("admin-add-order");

        return mv;
    }
}
