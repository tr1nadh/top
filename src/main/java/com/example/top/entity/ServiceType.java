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
public class ServiceType {

    @Id
    @SequenceGenerator(
            name = "service_type_seq",
            sequenceName = "service_type_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "service_type_seq"
    )
    private Long serviceTypeId;
    @Column(unique = true)
    private String name;
}
