package com.example.top.repository;

import com.example.top.entity.order.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
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
    List<Order> findOrdersByOrderStatusAndServiceServiceTypeName(String orderStatus, String serviceType, Pageable pageable);
    List<Order> findOrdersByOrderStatusAndHandleByNameAndServiceServiceTypeName(String orderStatus,
                                                                                String handleByName,
                                                                                String serviceType, Pageable pageable);

    List<Order> findOrdersByOrderStatusAndServiceBookingDate(String orderStatus, LocalDate date, Pageable pageable);
    List<Order> findOrdersByOrderStatusAndServiceBookingDateBetween(String orderStatus,
                                                                    LocalDate startDate, LocalDate endDate,
                                                                    Pageable pageable);
}
