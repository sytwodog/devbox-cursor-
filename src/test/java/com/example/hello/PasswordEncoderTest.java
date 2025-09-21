package com.example.hello;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    
    @Test
    public void generateEncodedPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "123456"; // 您要加密的密码
        String encodedPassword = encoder.encode(password);
        System.out.println("加密后的密码: " + encodedPassword);
    }
}


