package com.example.top.repository;

import com.example.top.entity.order.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByCustomerNameContaining(String name);
    List<Order> findOrdersByOrderStatus(String status);
    List<Order> findOrdersByOrderStatus(String status, Pageable pageable);
    List<Order> findOrdersByOrderStatusAndCustomerNameContaining(String orderStatus, String name, Pageable pageable);
    List<Order> findOrdersByOrderStatusAndHandleByFullName(String orderStatus, String name);
    List<Order> findOrdersByOrderStatusAndHandleByFullNameAndCustomerNameContaining(String orderStatus,
                                                                                    String handleByName, String customerName);
}
