package com.example.top.service;

import com.example.top.entity.employee.Account;
import com.example.top.entity.employee.Employee;
import com.example.top.exception.DuplicateException;
import com.example.top.repository.AccountRepository;
import com.example.top.repository.EmployeeRepository;
import com.example.top.util.GeneralUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void saveEmployee(Employee employee) {
        if (employee == null) throw new IllegalArgumentException("'employee' cannot be null");

        if (employee.getAccount() == null) {
            repository.save(employee);
        } else {
            employee.getAccount().setEmployee(employee);
            repository.save(checkPasswordChange(employee));
        }

        log.info("Employee '" + employee.getFullName() + "' has been saved" );
    }

    public Employee checkPasswordChange(Employee employee) {
        var account = employee.getAccount();
        if (account.isPasswordChanged()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setPasswordChanged(false);
        }

        return employee;
    }

    public void saveAccount(Long empId, Account account) {
        if (empId == null) throw new IllegalArgumentException("'empId' cannot be null");
        if (account == null) throw new IllegalArgumentException("'account' cannot be null");

        var optDbEmployee = repository.findById(empId);
        if (optDbEmployee.isEmpty())
            throw new IllegalStateException("Cannot save account to an employee: No employee exists with the id '" + empId + "'");

        var dbEmployee = optDbEmployee.get();
        var username = account.getUsername();
        if (!dbEmployee.getAccount().getUsername().equals(username) &&
                isUsernameAlreadyExists(username))
            throw new DuplicateException("Username '" + username + "' already exists");

        if (isThisANewAccount(dbEmployee)) {
            if (GeneralUtil.isQualifiedString(account.getPassword())) {
                account.setEmployee(dbEmployee);
                dbEmployee.setAccount(account);
                repository.save(checkPasswordChange(dbEmployee));
                return;
            } else throw new IllegalStateException("Cannot save account without a password");
        }

        if (GeneralUtil.isQualifiedString(account.getPassword())) {
            account.setEmployee(dbEmployee);
            dbEmployee.setAccount(account);
        } else dbEmployee.getAccount().setUsername(username);

        repository.save(checkPasswordChange(dbEmployee));

        log.info("Account has been successfully saved to an employee of id " + empId);
    }

    private boolean isThisANewAccount(Employee dbEmployee) {
        return dbEmployee.getAccount().getPassword() == null;
    }

    private boolean isUsernameAlreadyExists(String username) {
        return accountRepository.findByUsername(username).size() > 0;
    }

    public List<Employee> findAllEmployees() {
        var employees = repository
                .findByRoleNameNotIn(List.of("Admin", "Developer"));

        log.info("Successfully retrieved all employees");
        return employees;
    }

    public Employee getEmployee(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optEmployee = repository.findById(id);
        if (optEmployee.isEmpty()) {
             log.severe("No employee found with the id '" + id + "'");
             return null;
        }

        var employee = optEmployee.get();
        log.info("Employee '" + employee.getFullName() + "' has been retrieved");
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

    public void deleteEmployee(Long id) {
        if (id == null) throw new IllegalArgumentException("'id' cannot be null");

        var optEmployee = repository.findById(id);
        if (optEmployee.isEmpty())
            throw new IllegalStateException("Cannot delete employee: No employee exists with the id '" + id + "'");

        repository.deleteById(id);

        log.info("Employee '" + optEmployee.get().getFullName() + "' has been deleted");
    }
}
