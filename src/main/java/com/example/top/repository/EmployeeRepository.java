package com.example.top.repository;

import com.example.top.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByRoleNameNotIn(List<String> names);
}
