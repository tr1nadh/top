package com.example.top.service;

import com.example.top.entity.Account;
import com.example.top.exception.NoSuchAccount;
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
        if (account == null) throw new IllegalArgumentException("The object 'Account' cannot be null");

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
        if (!GeneralUtil.isQualifiedString(oldUsername)) throw new IllegalArgumentException("Old username not a qualified string");
        if (!GeneralUtil.isQualifiedString(newUsername)) throw new IllegalArgumentException("New username not a qualified string");

        var account = repository.findAccountByUsername(oldUsername);
        if (account == null) throw new NoSuchAccount("No account found with the username '" + oldUsername + "'");

        var auth = SecurityContextHolder.getContext().getAuthentication();
        var emp = (EmployeeDetails) auth.getPrincipal();
        emp.setUsername(newUsername);

        account.setUsername(newUsername);
        saveAccount(account);
        log.info("Account username successfully updated from '" + oldUsername + "' to '" + newUsername + "'");
    }

    public void updatePassword(String username, String oldPassword, String newPassword) {
        if (!GeneralUtil.isQualifiedString(username)) throw new IllegalArgumentException("Username not a qualified string");
        if (!GeneralUtil.isQualifiedString(oldPassword)) throw new IllegalArgumentException("Old password not a qualified string");
        if (!GeneralUtil.isQualifiedString(newPassword)) throw new IllegalArgumentException("New password not a qualified string");

        var account = repository.findAccountByUsername(username);
        if (account == null)
            throw new NoSuchAccount("No account found with the username '" + username + "'");

        if (!passwordEncoder.matches(oldPassword, account.getPassword()))
            throw new IllegalStateException("Cannot change to new password, Old password is wrong");

        account.setPassword(newPassword);
        saveAccount(account);
        log.info("Successfully updated the password of account username '" + username + "'");
    }
}
