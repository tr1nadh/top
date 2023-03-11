package com.example.top.repository;

import com.example.top.entity.EmployeeBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeBillRepository extends JpaRepository<EmployeeBill, Long> {
}
