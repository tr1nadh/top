package com.example.top.util.mapper;

import com.example.top.dto.employee.EmployeeDto;
import com.example.top.entity.employee.Account;
import com.example.top.entity.employee.Employee;
import com.example.top.entity.employee.Role;

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
        mappedEmp.setRole(mappedRole);
        Account mappedAccount = null;
        if (employeeDto.isHasAccount()) {
            mappedAccount = Mapper.map(employeeDto.getAccount(), new Account());
            mappedAccount.setEmployee(mappedEmp);
        }
        mappedEmp.setAccount(mappedAccount);

        return mappedEmp;
    }
}
