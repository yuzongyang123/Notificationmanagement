package com.example.notificationmanagement.service;

import com.example.notificationmanagement.pojo.UsersInformation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.notificationmanagement.pojo.UsersInformationAndactive;

import java.util.List;

/**
* @author Yuzongyang
* @description 针对表【users_information】的数据库操作Service
* @createDate 2024-06-08 16:55:21
*/
public interface UsersInformationService extends IService<UsersInformation> {
    public List<UsersInformationAndactive> getAllInformation() ;
    void deleteUser(Integer  userId);
    void resetpassword(int userid);
    List<UsersInformationAndactive> searchUsersInformation(String searchQuery);

    UsersInformation selectuser(String username);
    void updateUserByUsername(UsersInformation updatedUser);
    public int updateUserActiveStatus(int userId, boolean isactive) ;
    }
