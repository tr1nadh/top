package com.example.top.service;

import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.enums.PaymentStatus;
import com.example.top.enums.ServiceStatus;
import com.example.top.repository.OrderRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public void saveOrder(Order order) {
        if (order == null) {
            log.severe("Cannot add null as an order");
            return;
        }

        repository.save(applyStatus(order));

        log.info("Order with the id '" + order.getOrderId() + "' has been saved" );
    }

    public Order applyStatus(Order order) {
        var serviceStatus = order.getService().getServiceStatus();
        if (serviceStatus == null) return order;
        var paymentStatus = order.getPayment().getPaymentStatus();
        var orderStatus = order.getOrderStatus();

        if (serviceStatus.equals(ServiceStatus.COMPLETED.toString()) && paymentStatus.equals(PaymentStatus.PAID.toString())) {
            order.setOrderStatus(OrderStatus.COMPLETED.toString());
        } else if (!orderStatus.equals(OrderStatus.PENDING.toString())) {
            order.setOrderStatus(OrderStatus.PENDING.toString());
        }

        if (order.getPayment().getAmountPaid() == order.getPayment().getTotalAmount()) {
            order.getPayment().setPaymentStatus(PaymentStatus.PAID.toString());
        } else if (!paymentStatus.equals(PaymentStatus.UNPAID.toString())) {
            order.getPayment().setPaymentStatus(PaymentStatus.UNPAID.toString());
        }

        return order;
    }

    public List<Order> findAllOrders() {
        var orders = repository.findAll();

        log.info("Successfully retrieved all orders");
        return orders;
    }

    public Order getOrder(Long id) {
        var optionalOrder = repository.findById(id);

        if (optionalOrder.isEmpty()) {
            log.severe("No order found with the id '" + id + "'");
            return null;
        }

        log.info("Order with the id '" + id + "' has been retrieved");
        return optionalOrder.get();
    }

    public void deleteOrder(Long id) {
        var order = getOrder(id);
        if (order == null) {
            log.severe("Cannot delete the order with the id '" + id + "' which doesn't exists");
            return;
        }

        repository.deleteById(id);

        log.info("Order with the id '" + id + "' has been deleted");
    }

    public List<Order> findOrdersByCustomerNameContaining(String name) {
        var orders = repository.findOrdersByCustomerNameContaining(name);

        log.info("Successfully retrieved orders by customer name containing '" + name + "'");
        return orders;
    }

    public List<Order> findOrdersByOrderStatus(String status) {
        var orders = repository.findOrdersByOrderStatus(status);

        log.info("Successfully retrieved orders by order status '" + status + "'");
        return orders;
    }

    public List<Order> findOrdersByOrderStatusAndCustomerNameContaining(String status, String name) {
        var orders = repository.findOrdersByOrderStatusAndCustomerNameContaining(status, name);

        log.info("Successfully retrieved orders by order status '" + status + "' and customer name containing '" + name + "'");
        return orders;
    }
}
