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
    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String phoneNo;

    @Column(unique = true)
    private String emailAddress;

    @Column(unique = true)
    private String password;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private String gender;

    private boolean enabled;

}
