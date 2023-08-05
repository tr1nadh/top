package com.example.top.service.order;

import com.example.top.dto.ResponseDto;
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

    public ResponseDto getPersonalizedOrdersBy(OrderStatus status, int page) {
        var account = appSecurity.getCurrentLoggedInUserDetails();
        List<Order> orders;
        if (specialRoles.contains(account.getRole()))
            orders = repository.findOrdersByOrderStatus(status.toString(), getPageRequest(page));
        else orders = repository.findOrdersByOrderStatusAndHandleByName(
            status.toString(), account.getEmployee().getName(), getPageRequest(page));

        var message = "Successfully retrieved personalized orders by order status '"+ status +"' of page '"+ page +"'";
        return ResponseDto.builder().success(true).message(message).data(orders).build();
    }

    public ResponseDto getPersonalizedOrdersByCustomerNameContaining(OrderStatus status, String customerNameContaining, int page) {
        var account = appSecurity.getCurrentLoggedInUserDetails();
        List<Order> orders;
        if (specialRoles.contains(account.getRole()))
            orders = repository.findOrdersByOrderStatusAndCustomerNameContaining(status.toString(), customerNameContaining,
                    getPageRequest(page));
        else orders = repository.findOrdersByOrderStatusAndHandleByNameAndCustomerNameContaining(
                status.toString(), account.getEmployee().getName(), customerNameContaining, getPageRequest(page)
        );

        var message = "Successfully retrieved personalized orders by order status '"+ status +
                "' and customer name containing '"+ customerNameContaining + "' of page '"+ page +"'";
        return ResponseDto.builder().success(true).message(message).data(orders).build();
    }

    public ResponseDto getPersonalizedOrdersByPhoneNoContaining(OrderStatus status, String phoneNoContaining, int page) {
        var account = appSecurity.getCurrentLoggedInUserDetails();
        List<Order> orders;
        if (specialRoles.contains(account.getRole()))
            orders = repository.findOrdersByOrderStatusAndCustomerPhoneNoContaining(status.toString(), phoneNoContaining,
                    getPageRequest(page));
        else orders = repository.findOrdersByOrderStatusAndHandleByNameAndCustomerPhoneNoContaining(
                status.toString(), account.getEmployee().getName(), phoneNoContaining, getPageRequest(page));

        var message = "Successfully retrieved personalized orders by order status '"+ status +
                "' and phone number name containing '"+ phoneNoContaining + "' of page '"+ page +"'";
        return ResponseDto.builder().success(true).message(message).data(orders).build();
    }

    public ResponseDto getPersonalizedOrdersByEmailContaining(OrderStatus status, String emailContaining, int page) {
        var account = appSecurity.getCurrentLoggedInUserDetails();
        List<Order> orders;
        if (specialRoles.contains(account.getRole()))
             orders = repository.findOrdersByOrderStatusAndCustomerEmailAddressContaining(status.toString(), emailContaining,
                getPageRequest(page));
        else orders = repository.findOrdersByOrderStatusAndHandleByNameAndCustomerEmailAddressContaining(
                status.toString(), account.getEmployee().getName(), emailContaining, getPageRequest(page));

        var message = "Successfully retrieved personalized orders by order status '"+ status +
                "' and email containing '"+ emailContaining + "' of page '"+ page +"'";
        return ResponseDto.builder().success(true).message(message).data(orders).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DEVELOPER')")
    public ResponseDto findOrdersOrderStatusAndHandleBy(OrderStatus status, String handleBy, int page) {
        List<Order> orders = repository.findOrdersByOrderStatusAndHandleByName(status.toString(), handleBy, getPageRequest(page));

        var message = "Successfully retrieved personalized orders by order status '"+ status +
                "' and handle by '"+ handleBy + "' of page '"+ page +"'";
        return ResponseDto.builder().success(true).message(message).data(orders).build();
    }

    public ResponseDto findOrdersByBookingDate(OrderStatus status, LocalDate date, int page) {
        List<Order> orders = repository.findOrdersByOrderStatusAndServiceBookingDate(status.toString(), date, getPageRequest(page));

        var message = "Successfully retrieved personalized orders by order status '"+ status +
                "' and date '"+ date + "' of page '"+ page +"'";
        return ResponseDto.builder().success(true).message(message).data(orders).build();
    }

    public ResponseDto findOrdersByBookingDateBetween(OrderStatus status, LocalDate startDate, LocalDate endDate, int page) {
        List<Order> orders = repository.findOrdersByOrderStatusAndServiceBookingDateBetween(status.toString(),
                startDate, endDate, getPageRequest(page));

        var message = "Successfully retrieved personalized orders by order status '"+ status +
                "' and start date '"+ startDate + "' & end date '"+ endDate +"' of page '"+ page +"'";
        return ResponseDto.builder().success(true).message(message).data(orders).build();
    }

    private PageRequest getPageRequest(int page) {
        return PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "service.bookingDate"));
    }
}
