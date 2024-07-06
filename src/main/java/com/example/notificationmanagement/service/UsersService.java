package com.example.notificationmanagement.service;

import com.example.notificationmanagement.pojo.Users;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Yuzongyang
* @description 针对表【users】的数据库操作Service
* @createDate 2024-05-28 12:34:17
*/
public interface UsersService extends IService<Users> {
    public boolean authenticate(String username, String password);
    public int register(String username,String password);
    public void updateTokenVersion(String username) ;
    public int getTokenVersion(String username) ;
    public void logout(String username) ;
    }
