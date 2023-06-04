package com.example.top.service.order;

import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.repository.OrderRepository;
import com.example.top.service.ServiceHelper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class OrderFindService extends ServiceHelper {

    @Autowired
    private OrderRepository repository;

    public List<Order> getPersonalizedOrdersBy(OrderStatus status) {
        var account = getCurrentLoggedInUserDetails();
        var roleName = account.getRole();
        if (roleName.equals("ROLE_ADMIN") || roleName.equals("ROLE_DEVELOPER"))
            return findOrdersBy(status);

        return repository.findOrdersByOrderStatusAndHandleByFullName(status.toString(), account.getEmployee().getFullName());
    }

    public List<Order> getPersonalizedOrdersBy(OrderStatus status, String customerNameContaining) {
        var account = getCurrentLoggedInUserDetails();
        var roleName = account.getRole();
        if (roleName.equals("ROLE_ADMIN") || roleName.equals("ROLE_DEVELOPER"))
            return findOrdersBy(status, customerNameContaining);

        return repository.findOrdersByOrderStatusAndHandleByFullNameAndCustomerNameContaining(
                status.toString(), account.getEmployee().getFullName(), customerNameContaining
        );
    }

    private List<Order> findOrdersBy(OrderStatus status) {
        return repository.findOrdersByOrderStatus(status.toString());
    }

    private List<Order> findOrdersBy(OrderStatus status, String customerNameContaining) {
        return repository.findOrdersByOrderStatusAndCustomerNameContaining(status.toString(), customerNameContaining);
    }
}
