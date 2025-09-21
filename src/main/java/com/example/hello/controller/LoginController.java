package com.example.hello.controller;

import com.example.hello.common.Result;
import com.example.hello.mapper.EmpMapper;
import com.example.hello.pojo.Emp;
import com.example.hello.pojo.LoginRequest;
import com.example.hello.pojo.LoginResponse;
import com.example.hello.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    
    @Autowired
    private EmpMapper empMapper;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest loginRequest) {
        // 根据用户名查询员工
        Emp emp = empMapper.getByUsername(loginRequest.getUsername());
        
        // 如果员工不存在，返回错误信息
        if (emp == null) {
            return Result.error("用户名或密码错误");
        }
        
        // 验证密码
        if (!passwordEncoder.matches(loginRequest.getPassword(), emp.getPassword())) {
            return Result.error("用户名或密码错误");
        }
        
        // 生成token
        String token = jwtUtils.generateToken(emp.getId(), emp.getUsername());
        
        // 构建返回数据
        LoginResponse loginResponse = LoginResponse.builder()
                .id(emp.getId())
                .username(emp.getUsername())
                .name(emp.getName())
                .token(token)
                .build();
        
        return Result.success(loginResponse);
    }
}
