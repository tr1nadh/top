package com.example.top.entity.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "First name cannot be empty")
    private String firstname;
    @NotEmpty(message = "Last name cannot be empty")
    private String lastname;
    @OneToOne(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "role_id")
    private Role role;
//    @Pattern(regexp="(^$|[0-9]{10})",  message = "Phone no must be 10 digits")
    @Column(unique = true)
    private Long phoneNo;
    @Email(message = "Must be a valid email")
    @Column(unique = true)
    private String emailAddress;
    @NotEmpty(message = "Gender cannot be empty")
    private String gender;

}
