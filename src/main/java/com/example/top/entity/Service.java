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
public class Service {

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
    @Column(unique = true)
    private String name;
}
