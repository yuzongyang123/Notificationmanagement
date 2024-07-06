package com.example.notificationmanagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.notificationmanagement.pojo.ActiveStatus;
import com.example.notificationmanagement.pojo.UsersInformation;
import com.example.notificationmanagement.pojo.UsersInformationAndactive;
import com.example.notificationmanagement.service.UsersInformationService;
import com.example.notificationmanagement.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Api(tags = "用户管理")

public class UserController {

    private final UsersInformationService usersInformationService;

    @Autowired
    public UserController(UsersInformationService usersInformationService) {
        this.usersInformationService = usersInformationService;
    }


    @GetMapping("/getall")
    public List<UsersInformationAndactive> getAllInformation(@RequestParam(value = "search", required = false) String searchQuery) {
        if (searchQuery != null && !searchQuery.isEmpty()) {
            return usersInformationService.searchUsersInformation(searchQuery);
        } else {
            return usersInformationService.getAllInformation();
        }
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") int userId) {
        usersInformationService.deleteUser(userId);
    }
    @PutMapping("/{userId}/reset-password")
    public void resetPassword(@PathVariable("userId") int userId) {
    usersInformationService.resetpassword(userId);


    }
    @GetMapping("/selectuser")
    public UsersInformation selectuser(HttpServletRequest request){
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        Claims claims = JwtUtil.parseToken(token);
        String username = claims.getSubject();
        QueryWrapper<UsersInformation> wrapper=new QueryWrapper<UsersInformation>()
                .eq("username",username);
        UsersInformation usersInformation=usersInformationService.getOne(wrapper);

        return usersInformation;



    }

    @PostMapping("/updateuser")
    public UsersInformation updateUser(@RequestBody UsersInformation updatedUser) {
        QueryWrapper<UsersInformation> wrapper=new QueryWrapper<UsersInformation>()
                .eq("username",updatedUser.getUsername());
        usersInformationService.update(updatedUser,wrapper);
        return usersInformationService.getOne(wrapper);
    }

    @PutMapping("/{userId}/toggle-active")
    public int toggleActive(@PathVariable("userId") int userId, @RequestBody ActiveStatus activeStatus) {
        boolean newActiveStatus = activeStatus.isIsactive();
        return usersInformationService.updateUserActiveStatus(userId, newActiveStatus);
    }


}
