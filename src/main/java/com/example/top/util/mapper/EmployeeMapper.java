package com.example.top.util.mapper;

import com.example.top.dto.EmployeeDto;
import com.example.top.entity.employee.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {

    public static List<EmployeeDto> mapList(List<Employee> employees) {
        var list = new ArrayList<EmployeeDto>();
        for (var emp : employees) list.add(Mapper.map(emp, new EmployeeDto()));

        return list;
    }
}
