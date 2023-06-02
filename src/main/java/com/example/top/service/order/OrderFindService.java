package com.example.top.service.order;

import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.repository.OrderRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class OrderFindService {

    @Autowired
    private OrderRepository repository;

    public List<Order> findOrdersBy(OrderStatus status) {
        return repository.findOrdersByOrderStatus(status.toString());
    }

    public List<Order> findOrdersBy(OrderStatus status, String customerNameContaining) {
        return repository.findOrdersByOrderStatusAndCustomerNameContaining(status.toString(), customerNameContaining);
    }
}
