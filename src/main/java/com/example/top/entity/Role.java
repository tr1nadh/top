package com.example.top.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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
    private Long id;
    private String name;
}
