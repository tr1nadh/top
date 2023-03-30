package com.example.top.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long orderId;

    @Valid
    private CustomerDto customer;

    private EmployeeDto handleBy;

    @Valid
    private ServiceDto service;

    @Valid
    private PaymentDto payment;
}
