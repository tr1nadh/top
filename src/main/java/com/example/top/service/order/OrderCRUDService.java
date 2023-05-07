package com.example.top.service.order;

import com.example.top.entity.order.Order;
import com.example.top.repository.OrderRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Log
public class OrderCRUDService {

    @Autowired
    private OrderRepository repository;

    public void saveOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("'order' cannot be null");

        checkOrder(order);
        repository.save(order);

        log.info("Order with the id '" + order.getOrderId() + "' has been saved" );
    }

    private void checkOrder(Order order) {
        if (order.getOrderId() == null) {
            order.getService().setBookingDate(LocalDate.now());
            return;
        }

        var dbOrder = repository.findById(order.getOrderId());
        var prevBookingDate = dbOrder.get().getService().getBookingDate();
        order.getService().setBookingDate(prevBookingDate);
    }

    public Order getOrder(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optionalOrder = repository.findById(id);
        if (optionalOrder.isEmpty()) {
            log.severe("No order found with the id '" + id + "'");
            return null;
        }

        log.info("Order with the id '" + id + "' has been retrieved");
        return optionalOrder.get();
    }

    public void deleteOrder(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var order = repository.findById(id);
        if (order.isEmpty())
            throw new IllegalStateException("Cannot delete the order: No order exists with the id '" + id + "'");

        repository.deleteById(id);

        log.info("Order with the id '" + id + "' has been deleted");
    }
}
