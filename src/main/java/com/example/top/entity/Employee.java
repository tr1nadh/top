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
public class Employee {

    @Id
    @SequenceGenerator(
            name = "employee_seq",
            sequenceName = "employee_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_seq"
    )
    private Long id;
    @Column(unique = true)
    private String firstname;
    private String lastname;
    private String role;
    @Column(unique = true)
    private Long phoneNo;
    @Column(unique = true)
    private String emailAddress;
    private String gender;

}
