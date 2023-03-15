package com.example.top.controller;

import com.example.top.entity.order.Order;
import com.example.top.service.OrderService;
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
    private OrderService service;

    @GetMapping("/add-order")
    public ModelAndView addOrder() {
        var mv = new ModelAndView();
        mv.addObject("order", new Order());
        mv.setViewName("order/add-order");

        return mv;
    }

    @PostMapping("/save-employee")
    public RedirectView saveEmployee(Order order) {
        service.saveOrder(order);

        return new RedirectView("add-order");
    }

    @RequestMapping("/orders")
    public ModelAndView getOrders() {
        var mv = new ModelAndView();
        mv.addObject("orders", service.findAllOrders());
        mv.setViewName("order/order");

        return mv;
    }

    @GetMapping("/edit-order")
    public ModelAndView editOrder(Long id) {
        var order = service.getOrder(id);

        var mv = new ModelAndView();
        mv.addObject("order", order);
        mv.setViewName("order/edit-order");

        return mv;
    }

    @PostMapping("/update-employee")
    public RedirectView updateOrder(Order order) {
        service.updateOrder(order);

        return new RedirectView("orders");
    }

    @GetMapping("/delete-employee")
    public RedirectView deleteOrder(Long id) {
        service.deleteOrder(id);

        return new RedirectView("orders");
    }
}