package com.example.top.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmpUsernameDto {

    @NotEmpty(message = "Old username cannot be empty")
    private String oldUsername;

    @NotEmpty(message = "New username cannot be empty")
    private String newUsername;

    private String fromMapping;
}
