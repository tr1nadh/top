package com.example.top.entity.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
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
    private Long dimensionsId;
    @Column(unique = true)
    @NotEmpty(message = "Name cannot be empty")
    private String name;
}
