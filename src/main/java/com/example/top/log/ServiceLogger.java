package com.example.top.log;

import com.example.top.dto.ResponseDto;
import lombok.extern.java.Log;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log
public class ServiceLogger {

    @AfterReturning(value = "execution(com.example.top.dto.ResponseDto com.example.top.service.order.*.*(..))" +
            "|| execution(com.example.top.dto.ResponseDto com.example.top.service.*.*(..))", returning = "result")
    public void log(ResponseDto result) {
        log.info(result.getMessage());
    }
}
