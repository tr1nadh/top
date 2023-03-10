package com.example.top.repository;

import com.example.top.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Modifying
    int removeEmployeeByFirstname(String firstname);
}
