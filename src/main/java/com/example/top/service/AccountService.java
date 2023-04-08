package com.example.top.service;

import com.example.top.entity.Account;
import com.example.top.repository.AccountRepository;
import com.example.top.securitydetails.AccountDetails;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log
public class AccountService {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void saveAccount(Account account) {
        if (account == null) {
            log.severe("Cannot save null as an account");
            return;
        }

        repository.save(checkPasswordChange(account));

        log.info("Account with the username '" + account.getUsername() + "' has been saved" );
    }

    public Account checkPasswordChange(Account account) {
        if (account.isPasswordChanged()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setPasswordChanged(false);
        }

        return account;
    }

    public void updateUsername(String oldUsername, String newUsername) {
        var employee = repository.findAccountByUsername(oldUsername);

        if (employee == null) {
            log.severe("No account found with the username '" + oldUsername + "'");
            return;
        }

        var auth = SecurityContextHolder.getContext().getAuthentication();
        var emp = (AccountDetails) auth.getPrincipal();
        emp.setUsername(newUsername);

        employee.setUsername(newUsername);
        saveAccount(employee);
        log.info("Account username successfully updated from '" + oldUsername + "' to '" + newUsername + "'");
    }

    public void updatePassword(String username, String oldPassword, String newPassword) {
        var account = repository.findAccountByUsername(username);

        if (account == null) {
            log.severe("No account found with the username '" + username + "' to change password");
            return;
        }

        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            log.severe("Cannot change to new password, Old password is wrong");
            return;
        }

        account.setPassword(newPassword);
        saveAccount(account);
        log.info("Successfully updated the password of account username '" + username + "'");
    }
}
