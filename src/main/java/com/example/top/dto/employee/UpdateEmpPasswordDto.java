package com.example.top.dto.employee;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmpPasswordDto {

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Old password cannot be empty")
    private String oldPassword;

    @NotEmpty(message = "New password cannot be empty")
    private String newPassword;

    private String fromMapping;
}
