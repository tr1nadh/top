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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @GetMapping({"/new-order", "/update-order"})
    public ModelAndView renderOrder(Long id) {
        return getRenderView((id == null) ? new Order() : orderService.getOrder(id));
    }

    @PostMapping("/save-order")
    public ModelAndView saveOrder(@Valid @ModelAttribute("order") OrderDto order, BindingResult bindingResult, RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) return getRenderView(order);

        orderService.saveOrder(OrderMapper.map(order));

        var message = "";
        if (order.getOrderId() == null) message = "New order successfully saved!";
        else message = "Order '"+ order.getOrderId() +"' has been successfully updated!";
        attributes.addFlashAttribute("alertMessage", message);
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

    @GetMapping("/{status}")
    public ModelAndView getOrdersByStatus(@PathVariable("status") String status, String search_in,
                                         String search, String handle_by,
                                          String start_date, String end_date,
                                          @RequestParam(required = false) Integer page) {
        page = (page == null) ? 0 : page;
        if (search_in != null && search != null) return getSearchOrdersView(status, search_in, search, page);
        if (handle_by != null) return getHandleBySortView(status, handle_by, page);
        if (start_date != null) return getDateSortView(status, start_date, end_date, page);

        return getOrdersView(orderService.find.getPersonalizedOrdersBy(getOrderStatusEnum(status), page),
                status, page);
    }

    private ModelAndView getDateSortView(String status, String start_date, String end_date, Integer page) {
        List<Order> orders = null;
        if (GeneralUtil.isQualifiedString(start_date)) {
            if (!GeneralUtil.isQualifiedString(end_date))
                orders = orderService.find.findOrdersByBookingDate(getOrderStatusEnum(status), LocalDate.parse(start_date), page);
            else orders = orderService.find.findOrdersByBookingDateBetween(getOrderStatusEnum(status),
                        LocalDate.parse(start_date), LocalDate.parse(end_date), page);
        }

        var mv = getOrdersView(orders, status, page);
        mv.addObject("start_date", start_date);
        mv.addObject("end_date", end_date);
        return mv;
    }

    private ModelAndView getHandleBySortView(String status, String handle_by, Integer page) {
        var mv = getOrdersView(orderService.find.
                findOrdersOrderStatusAndHandleBy(getOrderStatusEnum(status), handle_by, page), status, page);
        mv.addObject("handle_by", handle_by);
        return mv;
    }

    private ModelAndView getSearchOrdersView(String status, String search_in, String search, int page) {
        List<Order> orders = new ArrayList<>();
        switch (search_in) {
            case "name" -> orders = orderService.find.getPersonalizedOrdersByCustomerNameContaining(getOrderStatusEnum(status), search, page);
            case "phoneNo" -> orders = orderService.find.getPersonalizedOrdersByPhoneNoContaining(getOrderStatusEnum(status), search, page);
            case "email" -> orders = orderService.find.getPersonalizedOrdersByEmailContaining(getOrderStatusEnum(status), search, page);
        }

        var mv = getOrdersView(orders, status, page);
        mv.addObject("search", search);
        mv.addObject("search_in", search_in);
        return mv;
    }

    private OrderStatus getOrderStatusEnum(String name) {
        for (var status : Arrays.stream(OrderStatus.values()).toList()) {
            if (name.toUpperCase().equals(status.toString()))
                return status;
        }

        return OrderStatus.PENDING;
    }

    private ModelAndView getOrdersView(List<Order> orders, String active, int page) {
        var mv = new ModelAndView();
        mv.addObject("orders", orders);
        mv.addObject("employees", employeeService.findAllEmployees());
        mv.addObject("active", active);
        mv.addObject("currentPage", page);
        mv.setViewName("order/order");

        return mv;
    }

    @PostMapping("/update-order-service-status")
    public RedirectView updateOrderServiceStatus(UpdateOrderServiceStatusDto updateOrder, RedirectAttributes attributes) {
        orderService.update.changeServiceStatus(updateOrder.getOrderId(), updateOrder.getServiceStatus());

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
        orderService.update.changeServiceStatus(id, serviceStatus);

        attributes.addFlashAttribute("alertMessage", "Order '" + id + "' service status changed to '" + serviceStatus + "'");
        return new RedirectView("pending");
    }

    @PostMapping("/change-payment-status")
    public RedirectView changePaymentStatus(Long id, String paymentStatus, RedirectAttributes attributes) {
        orderService.update.changePaymentStatus(id, paymentStatus);

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
        orderService.deleteOrder(id);

        attributes.addFlashAttribute("alertMessage", "Order '" + id + "' has been deleted");
        return new RedirectView("pending");
    }
}
