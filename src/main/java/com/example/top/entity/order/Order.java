package com.example.top.entity.order;

import com.example.top.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "OrgOrder")
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_seq",
            sequenceName = "order_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_seq"
    )
    private Long orderId;
    @Embedded
    private Customer customer;
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee handleBy;
    @Embedded
    private Service service;
    private int totalAmount;
    private int amountPaid;
}
