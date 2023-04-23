package com.example.top.service;

import com.example.top.entity.employee.Account;
import com.example.top.repository.AccountRepository;
import com.example.top.securitydetails.EmployeeDetails;
import com.example.top.util.GeneralUtil;
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
        if (account == null) throw new IllegalArgumentException("'account' cannot be null");

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
        if (!GeneralUtil.isQualifiedString(oldUsername)) throw new IllegalArgumentException("'oldUsername' is not a qualified string");
        if (!GeneralUtil.isQualifiedString(newUsername)) throw new IllegalArgumentException("'newUsername' is not a qualified string");

        var account = repository.findAccountByUsername(oldUsername);
        if (account == null) throw new IllegalStateException("Cannot update username: No account exists with the username " + oldUsername);

        updateUsernameInContext(newUsername);

        account.setUsername(newUsername);
        repository.save(account);
        log.info("Account username successfully changed from '" + oldUsername + "' to '" + newUsername + "'");
    }

    private void updateUsernameInContext(String newUsername) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var emp = (EmployeeDetails) auth.getPrincipal();
        emp.setUsername(newUsername);
    }

    public void updatePassword(String username, String oldPassword, String newPassword) {
        if (!GeneralUtil.isQualifiedString(username)) throw new IllegalArgumentException("'username' is not a qualified string");
        if (!GeneralUtil.isQualifiedString(oldPassword)) throw new IllegalArgumentException("'oldPassword' is not a qualified string");
        if (!GeneralUtil.isQualifiedString(newPassword)) throw new IllegalArgumentException("'newPassword' is not a qualified string");

        var account = repository.findAccountByUsername(username);
        if (account == null)
            throw new IllegalStateException("Cannot update password: No account exists with the username " + username);

        if (!passwordEncoder.matches(oldPassword, account.getPassword()))
            throw new IllegalStateException("Cannot change to new password: Old password is wrong");

        account.setPassword(newPassword);
        repository.save(checkPasswordChange(account));
        log.info("Successfully updated the password of account username '" + username + "'");
    }
}
