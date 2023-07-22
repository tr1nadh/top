package com.example.top.repository;

import com.example.top.entity.employee.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByRoleNameNotIn(List<String> names);
    List<Employee> findByRoleNameNotIn(List<String> names, Pageable pageable);
    List<Employee> findByRoleNameNotInAndNameContaining(List<String> names, String empNameContaining, Pageable pageable);
    List<Employee> findByRoleNameNotInAndPhoneNoContaining(List<String> names, String empPhoneNoContaining, Pageable pageable);
    List<Employee> findByRoleNameNotInAndEmailAddressContaining(List<String> names, String emailContaining, Pageable pageable);
}
