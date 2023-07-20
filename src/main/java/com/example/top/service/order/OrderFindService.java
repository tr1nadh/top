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

    public List<Order> getPersonalizedOrdersBy(OrderStatus status, int page) {
        var account = getCurrentLoggedInUserDetails();
        var roleName = account.getRole();
        if (roleName.equals("ROLE_ADMIN") || roleName.equals("ROLE_DEVELOPER"))
            return findOrdersBy(status, page);

        return repository.findOrdersByOrderStatusAndHandleByName(status.toString(), account.getEmployee().getName());
    }

    public List<Order> getPersonalizedOrdersBy(OrderStatus status, String customerNameContaining, int page) {
        var account = getCurrentLoggedInUserDetails();
        var roleName = account.getRole();
        if (roleName.equals("ROLE_ADMIN") || roleName.equals("ROLE_DEVELOPER"))
            return findOrdersBy(status, customerNameContaining, page);

        return repository.findOrdersByOrderStatusAndHandleByNameAndCustomerNameContaining(
                status.toString(), account.getEmployee().getName(), customerNameContaining
        );
    }

    public List<Order> getPersonalizedOrdersByPhoneNo(OrderStatus status, String phoneNo, int page) {
        var account = getCurrentLoggedInUserDetails();
        var roleName = account.getRole();
        if (roleName.equals("ROLE_ADMIN") || roleName.equals("ROLE_DEVELOPER"))
            return findOrdersByPhoneNo(status, phoneNo, page);

        return repository.findOrdersByOrderStatusAndHandleByNameAndCustomerPhoneNoContaining(
                status.toString(), account.getEmployee().getName(), phoneNo
        );
    }

    public List<Order> getPersonalizedOrdersByEmailAddress(OrderStatus status, String emailAddress, int page) {
        var account = getCurrentLoggedInUserDetails();
        var roleName = account.getRole();
        if (roleName.equals("ROLE_ADMIN") || roleName.equals("ROLE_DEVELOPER"))
            return findOrdersByEmail(status, emailAddress, page);

        return repository.findOrdersByOrderStatusAndHandleByNameAndCustomerEmailAddressContaining(
                status.toString(), account.getEmployee().getName(), emailAddress
        );
    }

    private List<Order> findOrdersBy(OrderStatus status, int page) {
        return repository.findOrdersByOrderStatus(status.toString(),
                PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "orderId")));
    }

    private List<Order> findOrdersBy(OrderStatus status, String customerNameContaining, int page) {
        return repository.findOrdersByOrderStatusAndCustomerNameContaining(status.toString(), customerNameContaining,
                PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "orderId")));
    }

    private List<Order> findOrdersByPhoneNo(OrderStatus status, String phoneNo, int page) {
        return repository.findOrdersByOrderStatusAndCustomerPhoneNoContaining(status.toString(), phoneNo,
                PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "orderId")));
    }

    private List<Order> findOrdersByEmail(OrderStatus status, String email, int page) {
        return repository.findOrdersByOrderStatusAndCustomerEmailAddressContaining(status.toString(), email,
                PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "orderId")));
    }
}
