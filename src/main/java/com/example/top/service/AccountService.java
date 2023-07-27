package com.example.top.service;

import com.example.top.entity.employee.Account;
import com.example.top.repository.AccountRepository;
import com.example.top.util.GeneralUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

@Service
@Log
public class AccountService extends ServiceHelper {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    public Account checkPasswordChange(Account account) {
        if (account.isPasswordChanged()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            account.setPasswordChanged(false);
        }

        return account;
    }

    public void changeUsername(String newUsername) {
        if (!GeneralUtil.isQualifiedString(newUsername)) throw new IllegalArgumentException("'newUsername' is not a qualified string");

        var currentAccount = getCurrentLoggedInUserDetails();
        var dbAccount = repository.findAccountByUsername(currentAccount.getUsername());
        if (newUsername.equals(currentAccount.getUsername()))
            throw new IllegalStateException("Cannot change username: New username is same as existing username");

        updateUsernameInContext(newUsername);

        dbAccount.setUsername(newUsername);
        repository.save(dbAccount);
        log.info("Account username successfully changed from '" + currentAccount.getUsername() + "' to '" + newUsername + "'");
    }

    private void updateUsernameInContext(String newUsername) {
        var emp = getCurrentLoggedInUserDetails();
        emp.setUsername(newUsername);
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!GeneralUtil.isQualifiedString(oldPassword)) throw new IllegalArgumentException("'oldPassword' is not a qualified string");
        if (!GeneralUtil.isQualifiedString(newPassword)) throw new IllegalArgumentException("'newPassword' is not a qualified string");

        var currentAccount = getCurrentLoggedInUserDetails();
        var dbAccount = repository.findAccountByUsername(currentAccount.getUsername());

        if (!passwordEncoder.matches(oldPassword, dbAccount.getPassword()))
            throw new IllegalStateException("Cannot change password: Old password is wrong");

        if (passwordEncoder.matches(newPassword, dbAccount.getPassword()))
            throw new IllegalStateException("Cannot change password: New password is same as existing password");

        dbAccount.setPassword(newPassword);
        repository.save(checkPasswordChange(dbAccount));

        logoutUser();

        log.info("Successfully changed the password of account username '" + currentAccount.getUsername() + "'");
    }

    private void logoutUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}
