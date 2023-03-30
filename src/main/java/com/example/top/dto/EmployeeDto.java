package com.example.top.dto;

import com.example.top.entity.employee.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Long employeeId;
    @NotEmpty(message = "First name cannot be empty")
    private String firstname;

    @NotEmpty(message = "Last name cannot be empty")
    private String lastname;

    private Role role;

    @Pattern(regexp="(^$|[0-9]{10})", message = "Phone no must be 10 digits")
    @NotEmpty(message = "Phone no cannot be empty")
    private String phoneNo;

    @Email(message = "Must be a valid email")
    @NotEmpty(message = "Email cannot be empty")
    private String emailAddress;

    @NotEmpty(message = "Gender cannot be empty")
    private String gender;
}
