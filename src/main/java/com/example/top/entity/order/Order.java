package com.example.top.entity.order;

import com.example.top.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamicUpdate
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
    @Embedded
    private Payment payment;
}
