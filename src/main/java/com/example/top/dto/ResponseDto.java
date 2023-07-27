package com.example.top.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private boolean success;
    private String message;
    private Object data;
}
