package com.example.top.service;

import com.example.top.entity.User;
import com.example.top.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class AdminService {

    @Autowired
    private UserRepository repository;

    public boolean changeUsername(String oldUsername, String newUsername) {
        var result = repository.updateUsernameByUsername(oldUsername, newUsername);

        if (result == 0) {
            log.severe("User with the username '" + oldUsername + "' not found");
            return false;
        }

        log.info("User with the username '" + oldUsername + "' has been changed to new username '" + newUsername + "'");
        return true;
    }

    public boolean changePassword(User user, String newPassword) {
        var username = user.getUsername();
        var userObj = repository.findByUsername(username);
        var oldPassword = userObj.getPassword();

        if (!user.getPassword().equals(oldPassword)) {
            log.severe("Password is wrong");
            return false;
        }

        var result = repository.updatePasswordByUsername(username, newPassword);

        System.out.println("result = " + result);

        log.info("Password of username '" + username + "' has been changed");

        return true;
    }
}
