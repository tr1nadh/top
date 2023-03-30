package com.example.top;

import com.example.top.dto.EmployeeDto;
import com.example.top.service.EmployeeService;
import com.example.top.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDtoConverter implements Converter<String, EmployeeDto> {

    @Autowired
    private EmployeeService service;

    @Override
    public EmployeeDto convert(String source) {
        var id = Long.parseLong(source);
        return Mapper.map(service.getEmployee(id), new EmployeeDto());
    }
}
