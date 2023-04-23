package com.example.top.entity.employee;

import com.example.top.repository.AccountRepository;
import com.example.top.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void test1() {
        var role = new Role();
        role.setRoleId(1L);
        role.setName("Fresher");

        var account = new Account();
        account.setUsername("fresh3");
        account.setPassword("fresh3@123");

        var employee = Employee.builder()
                .firstname("Fresh3")
                .lastname("Fresh3")
                .phoneNo("9822443622")
                .emailAddress("fresh3@gmail.com")
                .role(role)
                .gender("Male")
                .account(account)
                .build();


        employeeService.saveEmployee(employee);
    }

    @Transactional
    @Test
    public void test2() {
        var account = accountRepository.findById(6L);
        System.out.println("account = " + account.get().getEmployee());
        var employee = employeeService.getEmployee(10L);
        System.out.println("employee = " + employee);
    }
}