package com.example.top.util.mapper;

import com.example.top.dto.order.OrderDto;
import com.example.top.entity.employee.Employee;
import com.example.top.entity.order.Customer;
import com.example.top.entity.order.Order;
import com.example.top.entity.order.Payment;
import com.example.top.entity.order.Service;

public class OrderMapper {

    public static Order map(OrderDto orderDto) {
        var order = new Order();
        order.setOrderId(orderDto.getOrderId());
        order.setCustomer(Mapper.map(orderDto.getCustomer(), new Customer()));
        order.setHandleBy(Mapper.map(orderDto.getHandleBy(), new Employee()));
        order.setService(Mapper.map(orderDto.getService(), new Service()));
        order.setPayment(new Payment());

        return order;
    }
}
