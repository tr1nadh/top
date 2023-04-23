package com.example.top.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderServiceStatusDto {

    private Long orderId;
    private String serviceStatus;
}
