package com.example.top.service;

import com.example.top.entity.order.*;
import com.example.top.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private EmployeeService employeeService;

    @Test
    public void populateOrders() {
        var customer = Customer.builder()
                .name("customer 2")
                .phoneNo(134543L)
                .emailAddress("customer12@gmail.com")
                .build();

        var serviceTye = new ServiceType();
        serviceTye.setName("Album");

        var dimensions = new Dimensions();
        dimensions.setName("1180 X 680");

        var service = Service.builder()
                .serviceType(serviceTye)
                .bookingDate("date")
                .dimensions(dimensions)
                .quantity(2)
                .printingCharges(1200)
                .build();

        var payment = Payment.builder()
                .totalAmount(1000)
                .amountPaid(100)
                .paymentStatus("Unpaid")
                .build();

        var order = Order.builder()
                .customer(customer)
                .service(service)
                .handleBy(employeeService.getEmployee(113L))
                .payment(payment)
                .build();

        orderService.saveOrder(order);
    }

    @Test
    public void testFindByCustomerName() {
    }

    @Test
    public void testUpdateOrders() {
    }

    @Test
    public void testFindByServiceStatus() {
    }
}