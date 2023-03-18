package com.example.top.entity.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
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
