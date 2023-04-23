package com.example.top.converter;

import com.example.top.dto.employee.RoleDto;
import com.example.top.service.RoleService;
import com.example.top.util.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleDtoConverter implements Converter<String, RoleDto> {

    @Autowired
    private RoleService service;

    @Override
    public RoleDto convert(String source) {
        var id = Long.parseLong(source);
        return Mapper.map(service.getRole(id), new RoleDto());
    }
}
