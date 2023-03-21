package com.example.top.entity.order;

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

    private String name;
    private Long phoneNo;
    private String emailAddress;
}
