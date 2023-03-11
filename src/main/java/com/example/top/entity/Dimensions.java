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
public class Dimensions {

    @Id
    @SequenceGenerator(
            name = "dimensions_seq",
            sequenceName = "dimensions_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "dimensions_seq"
    )
    private Long id;
    @Column(unique = true)
    private String name;
}
