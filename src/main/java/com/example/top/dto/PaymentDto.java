package com.example.top.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    @NotNull(message = "Total amount cannot be null")
    @Min(value = 1, message = "Total amount cannot be zero")
    private int totalAmount;

    @NotNull(message = "Amount paid cannot be null")
    @Min(value = 1, message = "Amount paid cannot be zero")
    private int amountPaid;
}