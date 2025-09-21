package com.example.hello.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private Integer id;
    private String username;
    private String name;
    private String token;
}
