package com.example.top.service.order;

import com.example.top.entity.order.Order;
import com.example.top.repository.OrderRepository;
import com.example.top.util.GeneralUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class OrderFindService {

    @Autowired
    private OrderRepository repository;

    public List<Order> findOrdersByCustomerNameContaining(String name) {
        if (!GeneralUtil.isQualifiedString(name)) throw new IllegalArgumentException("'name' is not a qualified string");

        var orders = repository.findOrdersByCustomerNameContaining(name);

        log.info("Successfully retrieved orders by customer name containing '" + name + "'");
        return orders;
    }

    public List<Order> findOrdersByOrderStatus(String status) {
        if (!GeneralUtil.isQualifiedString(status)) throw new IllegalArgumentException("'status' is not a qualified string");

        var orders = repository.findOrdersByOrderStatus(status);

        log.info("Successfully retrieved orders by order status '" + status + "'");
        return orders;
    }

    public List<Order> findOrdersByOrderStatusAndCustomerNameContaining(String status, String name) {
        if (!GeneralUtil.isQualifiedString(status)) throw new IllegalArgumentException("'status' is not a qualified string");
        if (!GeneralUtil.isQualifiedString(name)) throw new IllegalArgumentException("'name' is not a qualified string");

        var orders = repository.findOrdersByOrderStatusAndCustomerNameContaining(status, name);

        log.info("Successfully retrieved orders by order status '" + status + "' and customer name containing '" + name + "'");
        return orders;
    }
}
