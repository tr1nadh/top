package com.example.top.service;

import com.example.top.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService service;

    @Test
    public void testToChangePassword() {
        var user = User.builder().username("some")
                .password("password").build();

        var result = service.changePassword(user, "securePass");

        System.out.println("result from test = " + result);
    }
}