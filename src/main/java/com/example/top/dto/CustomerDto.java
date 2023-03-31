package com.example.top.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Pattern(regexp="(^$|[0-9]{10})",  message = "Phone no must be 10 digits")
    @NotEmpty(message = "Phone no cannot be empty")
    private String phoneNo;

    @Email(message = "Must be a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    private String emailAddress;
}
