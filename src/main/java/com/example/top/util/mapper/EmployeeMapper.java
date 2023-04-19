package com.example.top.util.mapper;

import com.example.top.dto.EmployeeDto;
import com.example.top.entity.Account;
import com.example.top.entity.employee.Employee;
import com.example.top.entity.employee.Role;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {

    public static Employee mapInfo(EmployeeDto employeeDto) {
        var mappedEmp = Mapper.map(employeeDto, new Employee());
        var mappedRole = Mapper.map(employeeDto.getRole(), new Role());
        mappedEmp.setRole(mappedRole);

        return mappedEmp;
    }

    public static Employee map(EmployeeDto employeeDto) {
        var mappedEmp = Mapper.map(employeeDto, new Employee());
        var mappedRole = Mapper.map(employeeDto.getRole(), new Role());
        var mappedAccount = Mapper.map(employeeDto.getAccount(), new Account());
        mappedEmp.setRole(mappedRole);
        mappedEmp.setAccount(mappedAccount);

        return mappedEmp;
    }

    public static List<EmployeeDto> mapList(List<Employee> employees) {
        var list = new ArrayList<EmployeeDto>();
        for (var emp : employees) list.add(Mapper.map(emp, new EmployeeDto()));

        return list;
    }
}
