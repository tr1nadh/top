package com.example.top.controller;

import com.example.top.dto.OrderDto;
import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.enums.PaymentStatus;
import com.example.top.enums.ServiceStatus;
import com.example.top.service.DimensionsService;
import com.example.top.service.EmployeeService;
import com.example.top.service.OrderService;
import com.example.top.service.ServiceTypeService;
import com.example.top.util.OrderMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        mv.setViewName("order/save-order");

        return mv;
    }

    @PostMapping("/save-order")
    public ModelAndView saveOrder(@Valid @ModelAttribute("order") OrderDto order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var mv = new ModelAndView("order/save-order");
            mv.addObject("order", order);
            mv.addObject("employees", employeeService.findAllEmployees());
            mv.addObject("serviceTypes", serviceTypeService.findAllServiceTypes());
            mv.addObject("dimensions", dimensionsService.findAllDimensions());
            return mv;
        }

        orderService.saveOrder(OrderMapper.map(order));

        return new ModelAndView("redirect:/orders");
    }

    @GetMapping({"/", "/orders"})
    public ModelAndView getOrders(String search, String orderStatus) {
        var mv = new ModelAndView();
        mv.addObject("orders", orderService.getOrdersByParams(search, orderStatus));
        mv.addObject("order", new Order());
        mv.addObject("serviceStatus", Arrays.stream(ServiceStatus.values()).toList());
        mv.addObject("paymentStatus", Arrays.stream(PaymentStatus.values()).toList());
        mv.addObject("orderStatus", Arrays.stream(OrderStatus.values()).toList());
        mv.setViewName("order/order");

        return mv;
    }

    @PostMapping("/update-order")
    public RedirectView updateOrder(Order order, int addAm, int rmAm) {
        orderService.updateOrder(order, addAm, rmAm);

        return new RedirectView("orders");
    }

    @GetMapping("/cancel-order")
    public RedirectView cancelOrder(Long id) {
        orderService.cancelOrder(id);

        return new RedirectView("orders");
    }

    @GetMapping("/delete-order")
    public RedirectView deleteOrder(Long id) {
        orderService.deleteOrder(id);

        return new RedirectView("orders");
    }
}
