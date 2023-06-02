package com.example.top.controller;

import com.example.top.dto.order.AddAmountToOrderDto;
import com.example.top.dto.order.OrderDto;
import com.example.top.dto.order.RemoveOrderAmountDto;
import com.example.top.dto.order.UpdateOrderServiceStatusDto;
import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.service.DimensionsService;
import com.example.top.service.EmployeeService;
import com.example.top.service.ServiceTypeService;
import com.example.top.service.order.OrderService;
import com.example.top.util.GeneralUtil;
import com.example.top.util.mapper.OrderMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping({"/","/orders"})
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
        return new ModelAndView("redirect:pending");
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

    @GetMapping({"/", "/pending"})
    public ModelAndView getPendingOrders(String search) {
        if (GeneralUtil.isQualifiedString(search))
            return getOrdersViewByOrderStatus(orderService.find.findOrdersBy(OrderStatus.PENDING, search), "pending");

        return getOrdersViewByOrderStatus(orderService.find.findOrdersBy(OrderStatus.PENDING), "pending");
    }

    @GetMapping("/completed")
    public ModelAndView getCompletedOrders(String search) {
        if (GeneralUtil.isQualifiedString(search))
            return getOrdersViewByOrderStatus(orderService.find.findOrdersBy(OrderStatus.COMPLETED, search), "completed");

        return getOrdersViewByOrderStatus(orderService.find.findOrdersBy(OrderStatus.COMPLETED), "completed");
    }

    @GetMapping("/cancelled")
    public ModelAndView getCancelledOrders(String search) {
        if (GeneralUtil.isQualifiedString(search))
            return getOrdersViewByOrderStatus(orderService.find.findOrdersBy(OrderStatus.CANCELLED, search), "cancelled");

        return getOrdersViewByOrderStatus(orderService.find.findOrdersBy(OrderStatus.CANCELLED), "cancelled");
    }

    private ModelAndView getOrdersViewByOrderStatus(List<Order> orders, String active) {
        var mv = new ModelAndView();
        mv.addObject("orders", orders);
        mv.addObject("active", active);
        mv.setViewName("order/order");

        return mv;
    }

    @PostMapping("/update-order-service-status")
    @PreAuthorize("!hasRole('ADMIN')")
    public RedirectView updateOrderServiceStatus(UpdateOrderServiceStatusDto updateOrder, RedirectAttributes attributes) {
        orderService.update.updateServiceStatus(updateOrder.getOrderId(), updateOrder.getServiceStatus());

        attributes.addFlashAttribute("alertMessage", "Order '" + updateOrder.getOrderId() + "' service status changed to '" + updateOrder.getServiceStatus() + "'");
        return new RedirectView("pending");
    }

    @PostMapping("/add-order-amount")
    public RedirectView addOrderAmount(@Valid AddAmountToOrderDto updateOrder, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            var error = bindingResult.getFieldError("addAmount");

            attributes.addFlashAttribute("alertMessage", error.getDefaultMessage());
            return new RedirectView("pending");
        }

        orderService.update.addAmount(updateOrder.getOrderId(), updateOrder.getAddAmount());

        attributes.addFlashAttribute("alertMessage", "Amount '" + updateOrder.getAddAmount() + "' added to order '" + updateOrder.getOrderId() + "'");
        return new RedirectView("pending");
    }

    @PostMapping("/remove-order-amount")
    public RedirectView removeOrderAmount(@Valid RemoveOrderAmountDto updateOrder, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            var error = bindingResult.getFieldError("removeAmount");

            attributes.addFlashAttribute("alertMessage", error.getDefaultMessage());
            return new RedirectView("pending");
        }

        orderService.update.removeAmount(updateOrder.getOrderId(), updateOrder.getRemoveAmount());

        attributes.addFlashAttribute("alertMessage", "Amount '" + updateOrder.getRemoveAmount() + "' removed from order '" + updateOrder.getOrderId() + "'");
        return new RedirectView("pending");
    }

    @GetMapping("/move-order-pending")
    public RedirectView moveOrderToPending(Long id, RedirectAttributes attributes) {
        orderService.moveOrderToPending(id);

        attributes.addFlashAttribute("alertMessage", "Order '" + id + "' moved to 'PENDING'");
        return new RedirectView("pending");
    }

    @PostMapping("/change-service-status")
    public RedirectView changeServiceStatus(Long id, String serviceStatus, RedirectAttributes attributes) {
        orderService.update.updateServiceStatus(id, serviceStatus);

        attributes.addFlashAttribute("alertMessage", "Order '" + id + "' service status changed to '" + serviceStatus + "'");
        return new RedirectView("pending");
    }

    @PostMapping("/change-payment-status")
    public RedirectView changePaymentStatus(Long id, String paymentStatus, RedirectAttributes attributes) {
        orderService.update.updatePaymentStatus(id, paymentStatus);

        attributes.addFlashAttribute("alertMessage", "Order '" + id + "' payment status changed to '" + paymentStatus + "'");
        return new RedirectView("pending");
    }

    @GetMapping("/cancel-order")
    public RedirectView cancelOrder(Long id, RedirectAttributes attributes) {
        orderService.cancelOrder(id);

        attributes.addFlashAttribute("alertMessage", "Order '" + id + "' has been cancelled");
        return new RedirectView("pending");
    }

    @GetMapping("/delete-order")
    public RedirectView deleteOrder(Long id, RedirectAttributes attributes) {
        orderService.crud.deleteOrder(id);

        attributes.addFlashAttribute("alertMessage", "Order '" + id + "' has been deleted");
        return new RedirectView("pending");
    }
}
