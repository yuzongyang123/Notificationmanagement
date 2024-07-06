package com.example.notificationmanagement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.notificationmanagement.pojo.UserNotifications;
import com.example.notificationmanagement.service.UserNotificationsService;
import com.example.notificationmanagement.mapper.UserNotificationsMapper;
import org.springframework.stereotype.Service;

/**
* @author Yuzongyang
* @description 针对表【user_notifications】的数据库操作Service实现
* @createDate 2024-05-28 12:34:17
*/
@Service
public class UserNotificationsServiceImpl extends ServiceImpl<UserNotificationsMapper, UserNotifications>
    implements UserNotificationsService{

}




