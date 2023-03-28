package com.example.top.entity.employee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Role {

    @Id
    @SequenceGenerator(
            name = "role_seq",
            sequenceName = "role_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_seq"
    )
    private Long roleId;

    @Column(unique = true)
    private String name;
}
