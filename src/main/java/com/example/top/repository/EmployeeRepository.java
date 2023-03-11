package com.example.top.repository;

import com.example.top.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Modifying
    int deleteEmployeeByFirstname(String firstname);

    @Modifying
    @Query("update Employee e set e.firstname = ?1, e.lastname = ?2, e.role = ?3, " +
            "e.phoneNo = ?4, e.emailAddress = ?5, e.gender = ?6 where id = ?7")
    int updateEmployeeById(String firstname, String lastname, String role,
                           Long phoneNo, String emailAddress, String gender, Long id);
}
