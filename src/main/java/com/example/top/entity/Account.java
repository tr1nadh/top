package com.example.top.entity;

import com.example.top.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "employee")
public class Account {

    @Id
    @SequenceGenerator(
            name = "account_seq",
            sequenceName = "account_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_seq"
    )
    private Long accountId;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String password;

    @Transient
    private boolean passwordChanged;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public void setPassword(String password) {
        if (this.password == null ||
                !this.password.equals(password)) {
            passwordChanged = true;
            this.password = password;
        }
    }
}