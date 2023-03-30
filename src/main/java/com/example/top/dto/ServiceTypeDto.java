package com.example.top.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTypeDto {

    private Long serviceTypeId;

    @NotEmpty(message = "Name cannot be empty")
    private String name;
}
