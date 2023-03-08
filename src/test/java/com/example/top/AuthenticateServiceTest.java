package com.example.top;

import com.example.top.service.AuthenticateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class AuthenticateServiceTest {

    @Autowired
    private AuthenticateService authenticateService;

    @Test
    public void testIfUsernameIsWrong() {
        var result = authenticateService.auth("nothing", "password");

        Assert.isTrue(!result, "Workings");
    }
}