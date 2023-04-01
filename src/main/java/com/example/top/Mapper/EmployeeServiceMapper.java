package com.example.top.Mapper;

import com.example.top.dto.EmployeeDto;
import com.example.top.entity.employee.Employee;
import com.example.top.util.mapper.EmployeeMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class EmployeeServiceMapper {

    @Around("execution(java.util.List com.example.top.service.EmployeeService.findAllEmployees())")
    public List<EmployeeDto> mapFindAllEmployees(ProceedingJoinPoint pjp) throws Throwable {
        return EmployeeMapper.mapList((List<Employee>) pjp.proceed());
    }
}
