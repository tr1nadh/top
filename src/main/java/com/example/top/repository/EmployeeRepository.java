package com.example.top.repository;

import com.example.top.entity.employee.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findEmployeeByAccountUsername(String username);
    List<Employee> findByRoleNameNotIn(List<String> names);
    List<Employee> findByRoleNameNotIn(List<String> names, Pageable pageable);
}
