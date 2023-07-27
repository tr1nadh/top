package com.example.top.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    private boolean success;
    private String message;
    private Object data;
}
