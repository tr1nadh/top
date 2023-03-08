package com.example.top.service;

import com.example.top.repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log
public class AuthenticateService {

    @Autowired
    private UserRepository repository;

    public boolean auth(String username, String password) {
        var user = repository.findByUsername(username);

        if (user == null) {
            log.severe("No user found with the username '" + username + "'");
            return false;
        }

        var storedPassword = user.getPassword();

        if (!password.equals(storedPassword)) {
            log.severe("Password is wrong");
            return false;
        }

        log.info("User with the username '" + username + "' is authenticated");
        return true;
    }
}
