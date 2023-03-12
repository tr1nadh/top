package com.example.top.service;

import com.example.top.entity.Order;
import com.example.top.repository.OrderRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public boolean addOrder(Order order) {
        if (order == null) {
            log.severe("Cannot add null as an order");
            return false;
        }

        repository.save(order);

        log.info("Order with the id '" + order.getId() + "' has been added" );
        return true;
    }
}
