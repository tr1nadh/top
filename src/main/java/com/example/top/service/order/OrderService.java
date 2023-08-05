package com.example.top.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    @Autowired
    public OrderCRUDService curd;

    @Autowired
    public OrderUpdateService update;

    @Autowired
    public OrderFindService find;
}
