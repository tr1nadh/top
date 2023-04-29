package com.example.top.controller;

import com.example.top.dto.order.AddAmountToOrderDto;
import com.example.top.dto.order.OrderDto;
import com.example.top.dto.order.RemoveOrderAmountDto;
import com.example.top.dto.order.UpdateOrderServiceStatusDto;
import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.enums.PaymentStatus;
import com.example.top.enums.ServiceStatus;
import com.example.top.service.DimensionsService;
import com.example.top.service.EmployeeService;
import com.example.top.service.ServiceTypeService;
import com.example.top.service.order.OrderService;
import com.example.top.util.mapper.OrderMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
public class OrderController extends AController {

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
        return getRenderView((id == null) ? new Order() : orderService.crud.getOrder(id));
    }

    @PostMapping("/save-order")
    public ModelAndView saveOrder(@Valid @ModelAttribute("order") OrderDto order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return getRenderView(order);

        orderService.crud.saveOrder(OrderMapper.map(order));

        return getAlertView("Order saved", "/orders");
    }

    private ModelAndView getRenderView(Object order) {
        var mv = new ModelAndView();
        mv.addObject("order", order);
        mv.addObject("employees", employeeService.findAllEmployees());
        mv.addObject("serviceTypes", serviceTypeService.findAllServiceTypes());
        mv.addObject("dimensions", dimensionsService.findAllDimensions());
        mv.setViewName("order/save-order");

        return mv;
    }

    @GetMapping({"/", "/orders"})
    public ModelAndView getOrders(String search, String orderStatus) {
        var mv = new ModelAndView();
        mv.addObject("orders", orderService.params.getOrdersByParams(search, orderStatus));
        mv.addObject("order", new Order());
        mv.addObject("serviceStatus", Arrays.stream(ServiceStatus.values()).toList());
        mv.addObject("paymentStatus", Arrays.stream(PaymentStatus.values()).toList());
        mv.addObject("orderStatus", Arrays.stream(OrderStatus.values()).toList());
        mv.setViewName("order/order");

        return mv;
    }

    @PostMapping("/update-order-service-status")
    public ModelAndView updateOrderServiceStatus(UpdateOrderServiceStatusDto updateOrder) {
        orderService.update.updateServiceStatus(updateOrder.getOrderId(), updateOrder.getServiceStatus());

        return getAlertView("Service status updated", "/orders");
    }

    @PostMapping("/add-order-amount")
    public ModelAndView addOrderAmount(@Valid AddAmountToOrderDto updateOrder, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var error = bindingResult.getFieldError("addAmount");
            return getAlertView(error.getDefaultMessage(), "/orders");
        }

        orderService.update.addAmount(updateOrder.getOrderId(), updateOrder.getAddAmount());

        return getAlertView("Amount added", "/orders");
    }

    @PostMapping("/remove-order-amount")
    public ModelAndView removeOrderAmount(@Valid RemoveOrderAmountDto updateOrder, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var error = bindingResult.getFieldError("removeAmount");
            return getAlertView(error.getDefaultMessage(), "/orders");
        }

        orderService.update.removeAmount(updateOrder.getOrderId(), updateOrder.getRemoveAmount());

        return getAlertView("Amount removed", "/orders");
    }

    @GetMapping("/cancel-order")
    public ModelAndView cancelOrder(Long id) {
        orderService.cancelOrder(id);

        return getAlertView("Order cancelled", "/orders");
    }

    @GetMapping("/delete-order")
    public ModelAndView deleteOrder(Long id) {
        orderService.crud.deleteOrder(id);

        return getAlertView("Order deleted", "/orders");
    }
}
