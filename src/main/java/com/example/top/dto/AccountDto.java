package com.example.top.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long employeeId;

    private Long accountId;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    private String password;

    private EmployeeDto employee;
}
