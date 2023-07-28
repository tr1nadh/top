package com.example.top.service.order;

import com.example.top.entity.order.Order;
import com.example.top.enums.OrderStatus;
import com.example.top.repository.OrderRepository;
import com.example.top.security.userdetails.AppSecurity;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Log
public class OrderFindService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private AppSecurity appSecurity;
    private final List<String> specialRoles = List.of("ROLE_ADMIN", "ROLE_DEVELOPER");

    public List<Order> getPersonalizedOrdersBy(OrderStatus status, int page) {
        var account = appSecurity.getCurrentLoggedInUserDetails();
        if (specialRoles.contains(account.getRole()))
            return repository.findOrdersByOrderStatus(status.toString(), getPageRequest(page));

        return repository.findOrdersByOrderStatusAndHandleByName(
                status.toString(), account.getEmployee().getName(), getPageRequest(page));
    }

    public List<Order> getPersonalizedOrdersByCustomerNameContaining(OrderStatus status, String customerNameContaining, int page) {
        var account = appSecurity.getCurrentLoggedInUserDetails();
        if (specialRoles.contains(account.getRole()))
            return repository.findOrdersByOrderStatusAndCustomerNameContaining(status.toString(), customerNameContaining,
                    getPageRequest(page));

        return repository.findOrdersByOrderStatusAndHandleByNameAndCustomerNameContaining(
                status.toString(), account.getEmployee().getName(), customerNameContaining, getPageRequest(page)
        );
    }

    public List<Order> getPersonalizedOrdersByPhoneNoContaining(OrderStatus status, String phoneNoContaining, int page) {
        var account = appSecurity.getCurrentLoggedInUserDetails();
        if (specialRoles.contains(account.getRole()))
            return repository.findOrdersByOrderStatusAndCustomerPhoneNoContaining(status.toString(), phoneNoContaining,
                    getPageRequest(page));

        return repository.findOrdersByOrderStatusAndHandleByNameAndCustomerPhoneNoContaining(
                status.toString(), account.getEmployee().getName(), phoneNoContaining, getPageRequest(page)
        );
    }

    public List<Order> getPersonalizedOrdersByEmailContaining(OrderStatus status, String emailContaining, int page) {
        var account = appSecurity.getCurrentLoggedInUserDetails();
        if (specialRoles.contains(account.getRole())) return repository.findOrdersByOrderStatusAndCustomerEmailAddressContaining(status.toString(), emailContaining,
                getPageRequest(page));

        return repository.findOrdersByOrderStatusAndHandleByNameAndCustomerEmailAddressContaining(
                status.toString(), account.getEmployee().getName(), emailContaining, getPageRequest(page)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    public List<Order> findOrdersOrderStatusAndHandleBy(OrderStatus status, String handleBy, int page) {
        return repository.findOrdersByOrderStatusAndHandleByName(status.toString(), handleBy, getPageRequest(page));
    }

    public List<Order> findOrdersByBookingDate(OrderStatus status, LocalDate date, int page) {
        return repository.findOrdersByOrderStatusAndServiceBookingDate(status.toString(), date, getPageRequest(page));
    }

    public List<Order> findOrdersByBookingDateBetween(OrderStatus status, LocalDate startDate, LocalDate endDate, int page) {
        return repository.findOrdersByOrderStatusAndServiceBookingDateBetween(status.toString(),
                startDate, endDate, getPageRequest(page));
    }

    private PageRequest getPageRequest(int page) {
        return PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "service.bookingDate"));
    }
}
