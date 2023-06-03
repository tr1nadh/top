package com.example.top.service.order;

import com.example.top.entity.employee.Employee;
import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.repository.AccountRepository;
import com.example.top.repository.OrderRepository;
import com.example.top.security.userdetails.EmployeeDetails;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        var empDetails = getCurrentLoggedInUserDetails();
        var roleName = empDetails.getRole().getName();
        if (roleName.equals("ROLE_ADMIN") || roleName.equals("ROLE_DEVELOPER"))
            return findOrdersBy(status);

        return repository.findOrdersByOrderStatusAndHandleByFullName(status.toString(), empDetails.getFullName());
    }

    public List<Order> getPersonalizedOrdersBy(OrderStatus status, String customerNameContaining) {
        var empDetails = getCurrentLoggedInUserDetails();
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

    private Employee getCurrentLoggedInUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();
        return accountRepository.findAccountByUsername(employeeDetails.getUsername()).getEmployee();
    }
}
