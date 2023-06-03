package com.example.top.service.order;

import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.repository.AccountRepository;
import com.example.top.repository.OrderRepository;
import com.example.top.security.context.SecurityContext;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class OrderFindService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private AccountRepository accountRepository;

    public List<Order> getPersonalizedOrdersBy(OrderStatus status) {
        var empDetails = SecurityContext.getCurrentLoggedInUserDetails();
        var roleName = empDetails.getRole().getName();
        if (roleName.equals("ROLE_ADMIN") || roleName.equals("ROLE_DEVELOPER"))
            return findOrdersBy(status);

        return repository.findOrdersByOrderStatusAndHandleByFullName(status.toString(), empDetails.getFullName());
    }

    public List<Order> getPersonalizedOrdersBy(OrderStatus status, String customerNameContaining) {
        var empDetails = SecurityContext.getCurrentLoggedInUserDetails();
        var roleName = empDetails.getRole().getName();
        if (roleName.equals("ROLE_ADMIN") || roleName.equals("ROLE_DEVELOPER"))
            return findOrdersBy(status, customerNameContaining);

        return repository.findOrdersByOrderStatusAndHandleByFullNameAndCustomerNameContaining(
                status.toString(), empDetails.getFullName(), customerNameContaining
        );
    }

    private List<Order> findOrdersBy(OrderStatus status) {
        return repository.findOrdersByOrderStatus(status.toString());
    }

    private List<Order> findOrdersBy(OrderStatus status, String customerNameContaining) {
        return repository.findOrdersByOrderStatusAndCustomerNameContaining(status.toString(), customerNameContaining);
    }
}
