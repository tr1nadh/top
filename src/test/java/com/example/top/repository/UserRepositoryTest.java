package com.example.top.repository;

import com.example.top.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void findPasswordByUserName() {
        var user = repository.findByUsername("some");

        System.out.println("user = " + user);
    }

    @Test
    public void addUser() {
        var user = User.builder()
                        .username("some")
                                .password("password")
                                        .build();
        repository.save(user);
    }
}