package com.example.top.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "service")
public class EmployeeBill {

    @Id
    @SequenceGenerator(
            name = "service_seq",
            sequenceName = "service_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "service_seq"
    )
    private Long id;
    private String service;
    private String bookingDate;
    private String dimensions;
    private int noOfSheets;
    private int printingCharges;
}
