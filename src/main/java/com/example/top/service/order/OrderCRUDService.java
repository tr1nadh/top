package com.example.top.service.order;

import com.example.top.entity.order.Order;
import com.example.top.exception.UnknownIdException;
import com.example.top.repository.OrderRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Log
public class OrderCRUDService {

    @Autowired
    private OrderRepository repository;

    public void saveOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("The object 'Order' cannot be null");

        repository.save(order);

        log.info("Order with the id '" + order.getOrderId() + "' has been saved" );
    }

    public Order getOrder(Long id) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");

        var optionalOrder = repository.findById(id);
        if (optionalOrder.isEmpty()) throw new UnknownIdException("No order found with the id '" + id + "'");

        log.info("Order with the id '" + id + "' has been retrieved");
        return optionalOrder.get();
    }

    protected List<Order> findOrdersByCustomerNameContaining(String name) {
        var orders = repository.findOrdersByCustomerNameContaining(name);

        log.info("Successfully retrieved orders by customer name containing '" + name + "'");
        return orders;
    }

    protected List<Order> findOrdersByOrderStatus(String status) {
        var orders = repository.findOrdersByOrderStatus(status);

        log.info("Successfully retrieved orders by order status '" + status + "'");
        return orders;
    }

    protected List<Order> findOrdersByOrderStatusAndCustomerNameContaining(String status, String name) {
        var orders = repository.findOrdersByOrderStatusAndCustomerNameContaining(status, name);

        log.info("Successfully retrieved orders by order status '" + status + "' and customer name containing '" + name + "'");
        return orders;
    }

    public void deleteOrder(Long id) {
        if (id == null) throw new IllegalArgumentException("Id cannot be null");

        var order = getOrder(id);
        if (order == null) throw new IllegalStateException("Cannot delete the order with the id '" + id + "' which doesn't exists");

        repository.deleteById(id);

        log.info("Order with the id '" + id + "' has been deleted");
    }
}
