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
