package com.example.hello.interceptor;

import com.example.hello.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求头中的token
        String token = request.getHeader("token");
        
        // 如果token为空，返回401状态码
        if (!StringUtils.hasLength(token)) {
            response.setStatus(401);
            return false;
        }
        
        try {
            // 解析token
            Claims claims = jwtUtils.parseToken(token);
            // 判断token是否过期
            if (jwtUtils.isTokenExpired(claims)) {
                response.setStatus(401);
                return false;
            }
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}
