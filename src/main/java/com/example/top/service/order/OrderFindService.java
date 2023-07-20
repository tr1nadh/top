package com.example.top.service.order;

import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.repository.OrderRepository;
import com.example.top.service.ServiceHelper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class OrderFindService extends ServiceHelper {

    @Autowired
    private OrderRepository repository;
    private final List<String> excludeList = List.of("ROLE_ADMIN", "ROLE_DEVELOPER");

    public List<Order> getPersonalizedOrdersBy(OrderStatus status, int page) {
        var account = getCurrentLoggedInUserDetails();
        if (excludeList.contains(account.getRole())) return findOrdersBy(status, page);

        return repository.findOrdersByOrderStatusAndHandleByName(status.toString(), account.getEmployee().getName());
    }

    public List<Order> getPersonalizedOrdersByCustomerNameContaining(OrderStatus status, String customerNameContaining, int page) {
        var account = getCurrentLoggedInUserDetails();
        if (excludeList.contains(account.getRole())) return findOrdersByCustomerNameContaining(status, customerNameContaining, page);

        return repository.findOrdersByOrderStatusAndHandleByNameAndCustomerNameContaining(
                status.toString(), account.getEmployee().getName(), customerNameContaining
        );
    }

    public List<Order> getPersonalizedOrdersByPhoneNoContaining(OrderStatus status, String phoneNoContaining, int page) {
        var account = getCurrentLoggedInUserDetails();
        if (excludeList.contains(account.getRole())) return findOrdersByPhoneNoContaining(status, phoneNoContaining, page);

        return repository.findOrdersByOrderStatusAndHandleByNameAndCustomerPhoneNoContaining(
                status.toString(), account.getEmployee().getName(), phoneNoContaining
        );
    }

    public List<Order> getPersonalizedOrdersByEmailContaining(OrderStatus status, String emailContaining, int page) {
        var account = getCurrentLoggedInUserDetails();
        if (excludeList.contains(account.getRole())) return findOrdersByEmailContaining(status, emailContaining, page);

        return repository.findOrdersByOrderStatusAndHandleByNameAndCustomerEmailAddressContaining(
                status.toString(), account.getEmployee().getName(), emailContaining
        );
    }

    private List<Order> findOrdersBy(OrderStatus status, int page) {
        return repository.findOrdersByOrderStatus(status.toString(),
                PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "orderId")));
    }

    private List<Order> findOrdersByCustomerNameContaining(OrderStatus status, String customerNameContaining, int page) {
        return repository.findOrdersByOrderStatusAndCustomerNameContaining(status.toString(), customerNameContaining,
                PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "orderId")));
    }

    private List<Order> findOrdersByPhoneNoContaining(OrderStatus status, String phoneNoContaining, int page) {
        return repository.findOrdersByOrderStatusAndCustomerPhoneNoContaining(status.toString(), phoneNoContaining,
                PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "orderId")));
    }

    private List<Order> findOrdersByEmailContaining(OrderStatus status, String emailContaining, int page) {
        return repository.findOrdersByOrderStatusAndCustomerEmailAddressContaining(status.toString(), emailContaining,
                PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "orderId")));
    }
}
