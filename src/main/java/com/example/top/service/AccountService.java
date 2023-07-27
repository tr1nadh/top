package com.example.top.service;

import com.example.top.dto.ResponseDto;
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

    public ResponseDto changeUsername(String newUsername) {
        if (!GeneralUtil.isQualifiedString(newUsername)) throw new IllegalArgumentException("'newUsername' is not a qualified string");

        var currentUsername = getCurrentLoggedInUser().getUsername();
        var dbAccount = repository.findAccountByUsername(currentUsername);
        if (newUsername.equals(currentUsername))
            throw new IllegalStateException("'" + newUsername + "' is already taken!");

        updateUsernameInContext(newUsername);

        dbAccount.setUsername(newUsername);
        repository.save(dbAccount);

        var message = "Username successfully changed from '" + currentUsername + "' to '" + newUsername + "'";
        log.info(message);

        return ResponseDto.builder().success(true).message(message).build();
    }

    private void updateUsernameInContext(String newUsername) {
        var emp = getCurrentLoggedInUser();
        emp.setUsername(newUsername);
    }

    public ResponseDto changePassword(String oldPassword, String newPassword) {
        if (!GeneralUtil.isQualifiedString(oldPassword)) throw new IllegalArgumentException("'oldPassword' is not a qualified string");
        if (!GeneralUtil.isQualifiedString(newPassword)) throw new IllegalArgumentException("'newPassword' is not a qualified string");

        var currentUsername = getCurrentLoggedInUser().getUsername();
        var dbAccount = repository.findAccountByUsername(currentUsername);
        var currentPassword = dbAccount.getPassword();
        if (!arePasswordsMatching(oldPassword, currentPassword))
            throw new IllegalStateException("Cannot change password: Old password is wrong");

        if (arePasswordsMatching(newPassword, currentPassword))
            throw new IllegalStateException("Cannot change password: New password is same as existing password");

        dbAccount.setPassword(newPassword);
        repository.save(checkPasswordChange(dbAccount));

        logoutUser();

        var message = "Password changed successfully, please login again.";
        log.info(message);

        return ResponseDto.builder().success(true).message(message).build();
    }

    private boolean arePasswordsMatching(String newPassword, String currentPassword) {
        return passwordEncoder.matches(newPassword, currentPassword);
    }

    private void logoutUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}
