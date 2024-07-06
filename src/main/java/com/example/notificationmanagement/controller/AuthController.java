package com.example.notificationmanagement.controller;

import com.example.notificationmanagement.exception.UsernameAlreadyExistsException;
import com.example.notificationmanagement.pojo.Users;
import com.example.notificationmanagement.service.UsersService;
import com.example.notificationmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Users request, HttpServletResponse response) {
        String username = request.getUsername();
        String password = request.getPassword();
        boolean authenticated = usersService.authenticate(username, password);
        if (authenticated) {
            String token = JwtUtil.generateToken(username);
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // 确保通过 HTTPS 传输
            cookie.setPath("/");
            cookie.setMaxAge(86400); // 1 天



            response.addCookie(cookie);
            return ResponseEntity.ok().header("Authorization", token).body("登录成功");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误/账号被禁用");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Users request) {
        try {
            String username = request.getUsername();
            String password = request.getPassword();
            usersService.register(username, password);
            return ResponseEntity.ok("注册成功");
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // 打印异常堆栈信息，方便调试
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("注册失败：" + e.getMessage());
        }
    }
}
