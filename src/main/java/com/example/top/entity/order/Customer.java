package com.example.top.entity.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private Long phoneNo;
    @Column(unique = true)
    private String emailAddress;
}
