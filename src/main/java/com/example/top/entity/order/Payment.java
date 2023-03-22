package com.example.top.entity.order;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {

    @NotEmpty(message = "Total amount cannot be empty")
    private int totalAmount;
    @NotEmpty(message = "Amount paid cannot be empty")
    private int amountPaid;
    private String paymentStatus;
}
