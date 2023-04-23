package com.example.top.dto.order;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAmountToOrderDto {

    private Long orderId;

    @Min(value = 1, message = "Amount cannot be less then 0")
    private int addAmount;
}
