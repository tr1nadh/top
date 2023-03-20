package com.example.top.entity.order;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {

    private int totalAmount;
    private int amountPaid;
    private String paymentStatus;
}
