package com.example.top.repository;

import com.example.top.entity.order.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByCustomerNameContaining(String name);
    List<Order> findOrdersByOrderStatus(String status);
    List<Order> findOrdersByOrderStatus(String status, Pageable pageable);
    List<Order> findOrdersByOrderStatusAndCustomerNameContaining(String orderStatus, String customerNameContaining, Pageable pageable);
    List<Order> findOrdersByOrderStatusAndCustomerPhoneNoContaining(String orderStatus, String phoneNoContaining, Pageable pageable);
    List<Order> findOrdersByOrderStatusAndCustomerEmailAddressContaining(String orderStatus, String emailAddressContaining, Pageable pageable);
    List<Order> findOrdersByOrderStatusAndHandleByName(String orderStatus, String name, Pageable pageable);
    List<Order> findOrdersByOrderStatusAndHandleByNameAndCustomerNameContaining(String orderStatus,
                                                                                String handleByName, String customerNameContaining, Pageable pageable);
    List<Order> findOrdersByOrderStatusAndHandleByNameAndCustomerPhoneNoContaining(String orderStatus,
                                                                                String handleByName, String phoneNoContaining, Pageable pageable);

    List<Order> findOrdersByOrderStatusAndHandleByNameAndCustomerEmailAddressContaining(String orderStatus,
                                                                                String handleByName, String emailAddressContaining, Pageable pageable);
}
