package com.example.top.service.order;

import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderParamsService {

    @Autowired
    private OrderFindService orderFindService;

    public List<Order> getOrdersByParams(String search, String orderStatus) {
        List<Order> orders;
        if (GeneralUtil.isQualifiedString(orderStatus)) orders = getOrdersBySearchAndOrderStatus(search, orderStatus);
        else if (!GeneralUtil.isQualifiedString(search)) orders = orderFindService.findOrdersByOrderStatus("Pending");
        else orders = orderFindService.findOrdersByCustomerNameContaining(search);

        return orders;
    }

    public List<Order> findOrders(OrderStatus status) {
        return orderFindService.findOrdersByOrderStatus(status.toString());
    }

    public List<Order> findPendingOrdersWithCustomerNameContaining(String name) {
        return orderFindService.findOrdersByOrderStatusAndCustomerNameContaining(OrderStatus.PENDING.toString(), name);
    }

    public List<Order> findCompletedOrdersWithCustomerNameContaining(String name) {
        return orderFindService.findOrdersByOrderStatusAndCustomerNameContaining(OrderStatus.COMPLETED.toString(), name);
    }

    public List<Order> findCancelledOrdersWithCustomerNameContaining(String name) {
        return orderFindService.findOrdersByOrderStatusAndCustomerNameContaining(OrderStatus.CANCELLED.toString(), name);
    }

    private List<Order> getOrdersBySearchAndOrderStatus(String search, String orderStatus) {
        List<Order> orders;
        if (!GeneralUtil.isQualifiedString(search)) orders = orderFindService.findOrdersByOrderStatus(orderStatus);
        else orders = orderFindService.findOrdersByOrderStatusAndCustomerNameContaining(orderStatus, search);

        return orders;
    }
}
