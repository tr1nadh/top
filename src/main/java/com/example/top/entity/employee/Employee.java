package com.example.top.entity.employee;

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
    private Long employeeId;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String phoneNo;

    @Column(unique = true)
    private String emailAddress;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private String gender;

    private boolean hasAccount;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Account account;
}
