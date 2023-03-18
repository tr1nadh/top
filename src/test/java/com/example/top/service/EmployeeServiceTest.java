package com.example.top.service;

import com.example.top.entity.employee.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService service;

    @Test
    public void testEmployeeMethods() {
        for (var i = 0; i < 100; i++) {
            var emp = Employee.builder().firstname("mani" + i)
                    .lastname("sharma" + i)
                    .gender("male").emailAddress("manisharme" + i + "@musictpp.com")
                    .phoneNo(8648785L + i).build();

            service.saveEmployee(emp);
        }
    }

    @Test
    public void testGetEmployee() {
        var emp = service.getEmployee(4L);

        System.out.println("emp = " + emp);
    }
}