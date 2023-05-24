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
import com.example.top.util.GeneralUtil;
import com.example.top.util.mapper.OrderMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;

@Controller
@RequestMapping("/orders")
public class OrderController extends AController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ServiceTypeService serviceTypeService;
    @Autowired
    private DimensionsService dimensionsService;

    @GetMapping({"/add-order", "/update-order"})
    public ModelAndView renderOrder(Long id) {
        return getRenderView((id == null) ? new Order() : orderService.crud.getOrder(id));
    }

    @PostMapping("/save-order")
    public ModelAndView saveOrder(@Valid @ModelAttribute("order") OrderDto order, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) return getRenderView(order);

        orderService.crud.saveOrder(OrderMapper.map(order));

        attributes.addFlashAttribute("alertMessage", "Order successfully saved");
        return new ModelAndView("redirect:/orders");
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

    @GetMapping("/orders")
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

    @GetMapping({"/pending"})
    public ModelAndView getPendingOrders(String search) {
        if (GeneralUtil.isQualifiedString(search)) return getPendingOrdersWithCustomerNameContaining(search);

        var mv = new ModelAndView();
        mv.addObject("orders", orderService.params.getPendingOrders());
        mv.addObject("order", new Order());
        mv.addObject("serviceStatus", Arrays.stream(ServiceStatus.values()).toList());
        mv.addObject("paymentStatus", Arrays.stream(PaymentStatus.values()).toList());
        mv.addObject("orderStatus", Arrays.stream(OrderStatus.values()).toList());
        mv.addObject("active", "pending");
        mv.setViewName("order/order");

        return mv;
    }

    public ModelAndView getPendingOrdersWithCustomerNameContaining(String search) {
        var mv = new ModelAndView();
        mv.addObject("orders", orderService.params.findPendingOrdersWithCustomerNameContaining(search));
        mv.addObject("order", new Order());
        mv.addObject("serviceStatus", Arrays.stream(ServiceStatus.values()).toList());
        mv.addObject("paymentStatus", Arrays.stream(PaymentStatus.values()).toList());
        mv.addObject("orderStatus", Arrays.stream(OrderStatus.values()).toList());
        mv.addObject("active", "pending");
        mv.setViewName("order/order");

        return mv;
    }

    @GetMapping("/completed")
    public ModelAndView getCompletedOrders(String search) {
        if (GeneralUtil.isQualifiedString(search)) return getCompletedOrdersWithCustomerNameContaining(search);

        var mv = new ModelAndView();
        mv.addObject("orders", orderService.params.getCompletedOrders());
        mv.addObject("order", new Order());
        mv.addObject("serviceStatus", Arrays.stream(ServiceStatus.values()).toList());
        mv.addObject("paymentStatus", Arrays.stream(PaymentStatus.values()).toList());
        mv.addObject("orderStatus", Arrays.stream(OrderStatus.values()).toList());
        mv.addObject("active", "completed");
        mv.setViewName("order/order");

        return mv;
    }

    public ModelAndView getCompletedOrdersWithCustomerNameContaining(String search) {
        var mv = new ModelAndView();
        mv.addObject("orders", orderService.params.findCompletedOrdersWithCustomerNameContaining(search));
        mv.addObject("order", new Order());
        mv.addObject("serviceStatus", Arrays.stream(ServiceStatus.values()).toList());
        mv.addObject("paymentStatus", Arrays.stream(PaymentStatus.values()).toList());
        mv.addObject("orderStatus", Arrays.stream(OrderStatus.values()).toList());
        mv.addObject("active", "completed");
        mv.setViewName("order/order");

        return mv;
    }

    @GetMapping("/cancelled")
    public ModelAndView getCancelledOrders(String search) {
        if (GeneralUtil.isQualifiedString(search)) return getCancelledOrdersWithCustomerNameContaining(search);

        var mv = new ModelAndView();
        mv.addObject("orders", orderService.params.getCancelledOrders());
        mv.addObject("order", new Order());
        mv.addObject("serviceStatus", Arrays.stream(ServiceStatus.values()).toList());
        mv.addObject("paymentStatus", Arrays.stream(PaymentStatus.values()).toList());
        mv.addObject("orderStatus", Arrays.stream(OrderStatus.values()).toList());
        mv.addObject("active", "cancelled");
        mv.setViewName("order/order");

        return mv;
    }

    public ModelAndView getCancelledOrdersWithCustomerNameContaining(String search) {
        var mv = new ModelAndView();
        mv.addObject("orders", orderService.params.findCancelledOrdersWithCustomerNameContaining(search));
        mv.addObject("order", new Order());
        mv.addObject("serviceStatus", Arrays.stream(ServiceStatus.values()).toList());
        mv.addObject("paymentStatus", Arrays.stream(PaymentStatus.values()).toList());
        mv.addObject("orderStatus", Arrays.stream(OrderStatus.values()).toList());
        mv.addObject("active", "cancelled");
        mv.setViewName("order/order");

        return mv;
    }

    @PostMapping("/update-order-service-status")
    public RedirectView updateOrderServiceStatus(UpdateOrderServiceStatusDto updateOrder, RedirectAttributes attributes) {
        orderService.update.updateServiceStatus(updateOrder.getOrderId(), updateOrder.getServiceStatus());

        attributes.addFlashAttribute("alertMessage", "Order '" + updateOrder.getOrderId() + "' service status changed to '" + updateOrder.getServiceStatus() + "'");
        return new RedirectView("/orders");
    }

    @PostMapping("/add-order-amount")
    public RedirectView addOrderAmount(@Valid AddAmountToOrderDto updateOrder, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            var error = bindingResult.getFieldError("addAmount");

            attributes.addFlashAttribute("alertMessage", error.getDefaultMessage());
            return new RedirectView("/orders");
        }

        orderService.update.addAmount(updateOrder.getOrderId(), updateOrder.getAddAmount());

        attributes.addFlashAttribute("alertMessage", "Amount '" + updateOrder.getAddAmount() + "' added to order '" + updateOrder.getOrderId() + "'");
        return new RedirectView("/orders");
    }

    @PostMapping("/remove-order-amount")
    public RedirectView removeOrderAmount(@Valid RemoveOrderAmountDto updateOrder, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            var error = bindingResult.getFieldError("removeAmount");

            attributes.addFlashAttribute("alertMessage", error.getDefaultMessage());
            return new RedirectView("/orders");
        }

        orderService.update.removeAmount(updateOrder.getOrderId(), updateOrder.getRemoveAmount());

        attributes.addFlashAttribute("alertMessage", "Amount '" + updateOrder.getRemoveAmount() + "' removed from order '" + updateOrder.getOrderId() + "'");
        return new RedirectView("/orders");
    }

    @GetMapping("/move-order-pending")
    public RedirectView moveOrderToPending(Long id, RedirectAttributes attributes) {
        orderService.moveOrderToPending(id);

        attributes.addFlashAttribute("alertMessage", "Order '" + id + "' moved to 'PENDING'");
        return new RedirectView("/orders");
    }

    @PostMapping("/change-service-status")
    public RedirectView changeServiceStatus(Long id, String serviceStatus, RedirectAttributes attributes) {
        orderService.update.updateServiceStatus(id, serviceStatus);

        attributes.addFlashAttribute("alertMessage", "Order '" + id + "' service status changed to '" + serviceStatus + "'");
        return new RedirectView("/orders");
    }

    @PostMapping("/change-payment-status")
    public RedirectView changePaymentStatus(Long id, String paymentStatus, RedirectAttributes attributes) {
        orderService.update.updatePaymentStatus(id, paymentStatus);

        attributes.addFlashAttribute("alertMessage", "Order '" + id + "' payment status changed to '" + paymentStatus + "'");
        return new RedirectView("/orders");
    }

    @GetMapping("/cancel-order")
    public RedirectView cancelOrder(Long id, RedirectAttributes attributes) {
        orderService.cancelOrder(id);

        attributes.addFlashAttribute("alertMessage", "Order '" + id + "' has been cancelled");
        return new RedirectView("/orders");
    }

    @GetMapping("/delete-order")
    public RedirectView deleteOrder(Long id, RedirectAttributes attributes) {
        orderService.crud.deleteOrder(id);

        attributes.addFlashAttribute("alertMessage", "Order '" + id + "' has been deleted");
        return new RedirectView("/orders");
    }
}
