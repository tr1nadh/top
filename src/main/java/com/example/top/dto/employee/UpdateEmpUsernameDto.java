package com.example.top.dto.employee;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmpUsernameDto {

    @NotEmpty(message = "New username cannot be empty")
    private String newUsername;
}
