package com.example.top.service;

import com.example.top.entity.Employee;
import com.example.top.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService service;

    @Test
    public void testAddMethod() {
        var emp = Employee.builder().firstname("mani")
                .lastname("sharma2").role("music director")
                .gender("male").emailAddress("manisharme@musictpp.com")
                .phoneNo(8648385921L).build();

        service.addEmployee(null);
    }

    @Test
    public void testFindAllMethod() {
        var employees = service.findAllEmployees();

        System.out.println("employees = " + employees);
    }

    @Test
    public void testRemoveMethod() {
        service.removeEmployee(Employee.builder().firstname("mani").build());
    }

    @Test
    public void testRoleMethods() {
        var role = new Role();
        role.setId(3L);
        service.removeRole(role);
    }
}