package com.example.top.service;

import com.example.top.dto.ResponseDto;
import com.example.top.entity.employee.Account;
import com.example.top.entity.employee.Employee;
import com.example.top.repository.AccountRepository;
import com.example.top.repository.EmployeeRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private final List<String> excludeRoles = List.of("Admin", "Developer");

    public ResponseDto saveEmployee(Employee employee) {
        if (employee == null) throw new IllegalArgumentException("'employee' cannot be null");

        repository.save(checkPasswordChange(employee));

        var message = (employee.getEmployeeId() == null) ? "New employee '"+ employee.getName() +"' has been saved successfully!" :
                "Employee '"+ employee.getName() +"' has been updated successfully!" ;
        log.info(message);

        return ResponseDto.builder().success(true).message(message).build();
    }

    public Employee checkPasswordChange(Employee employee) {
        var account = employee.getAccount();
        if (employee.isHasAccount() && account.isPasswordChanged()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setPasswordChanged(false);
        }

        return employee;
    }

    public ResponseDto saveAccount(Long empId, Account account) {
        if (empId == null) throw new IllegalArgumentException("'empId' cannot be null");
        if (account == null) throw new IllegalArgumentException("'account' cannot be null");

        var optDbEmployee = repository.findById(empId);
        if (optDbEmployee.isEmpty())
            throw new IllegalStateException("Cannot save account to an employee: No employee exists with the id '" + empId + "'");

        var dbEmployee = optDbEmployee.get();
        if (account.isPasswordChanged()) {
            if (!dbEmployee.isHasAccount()) dbEmployee.setHasAccount(true);
            account.setEmployee(dbEmployee);
            dbEmployee.setAccount(account);
        } else if (dbEmployee.isHasAccount() && account.isUsernameChanged())
            dbEmployee.getAccount().setUsername(account.getUsername());
        else if (!dbEmployee.isHasAccount()) throw new IllegalStateException("Cannot save account without a password");

        repository.save(checkPasswordChange(dbEmployee));

        log.info("Account has been successfully saved to an employee of id " + empId);
        var message = (account.getAccountId() == null) ? "Account created successfully" : "Account updated successfully";

        return ResponseDto.builder().success(true).message(message).build();
    }

    public List<Employee> findAllEmployees() {
        var employees = repository.findByRoleNameNotIn(excludeRoles);

        log.info("Successfully retrieved all employees");
        return employees;
    }

    public List<Employee> findAllEmployees(int page) {
        var employees = repository.findByRoleNameNotIn(excludeRoles,
                        PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "employeeId")));

        log.info("Successfully retrieved all employees");
        return employees;
    }

    public List<Employee> findEmployeesByNameContaining(String nameContaining, int page) {
        return repository.findByRoleNameNotInAndNameContaining(excludeRoles,
                nameContaining, PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "employeeId")));
    }

    public List<Employee> findEmployeesByPhoneNoContaining(String phoneNoContaining, int page) {
        return repository.findByRoleNameNotInAndPhoneNoContaining(excludeRoles, phoneNoContaining,
                PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "employeeId")));
    }

    public List<Employee> findEmployeesByEmailContaining(String emailContaining, int page) {
        return repository.findByRoleNameNotInAndEmailAddressContaining(excludeRoles, emailContaining,
                PageRequest.of(page, 15, Sort.by(Sort.Direction.DESC, "employeeId")));
    }

    public Employee getEmployee(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optEmployee = repository.findById(id);
        if (optEmployee.isEmpty()) {
             log.severe("No employee found with the id '" + id + "'");
             return null;
        }

        var employee = optEmployee.get();
        log.info("Employee '" + employee.getName() + "' has been retrieved");
        return employee;
    }

    public Account getAccount(Long empId) {
        if (empId == null) throw new IllegalArgumentException("'empId' cannot be null");

        var employee = repository.findById(empId);
        if (employee.isEmpty())
            throw new IllegalStateException("No employee exists with the id '" + empId + "'");

        var account = employee.get().getAccount();
        if (account == null) {
            log.severe("There isn't any account associated with the employee of id '" + empId + "'");
            return new Account();
        }

        log.info("Account with the employee id '" + empId + "' has been retrieved");
        return account;
    }

    public ResponseDto deleteEmployee(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optEmployee = repository.findById(id);
        if (optEmployee.isEmpty())
            throw new IllegalStateException("Cannot delete employee: No employee exists with the id '" + id + "'");

        repository.deleteById(id);

        var message = "Employee '" + optEmployee.get().getName() + "' has been deleted";
        log.info(message);

        return ResponseDto.builder().success(true).message(message).build();
    }
}
