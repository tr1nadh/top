package com.example.top.entity.order;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @Pattern(regexp="(^$|[0-9]{10})",  message = "Phone no must be 10 digits")
    private Long phoneNo;
    @Email(message = "Must be a valid email address")
    private String emailAddress;
}
