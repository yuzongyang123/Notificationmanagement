package com.example.notificationmanagement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.notificationmanagement.pojo.GroupMessages;
import com.example.notificationmanagement.service.GroupMessagesService;
import com.example.notificationmanagement.mapper.GroupMessagesMapper;
import org.springframework.stereotype.Service;

/**
* @author Yuzongyang
* @description 针对表【group_messages】的数据库操作Service实现
* @createDate 2024-05-28 12:34:17
*/
@Service
public class GroupMessagesServiceImpl extends ServiceImpl<GroupMessagesMapper, GroupMessages>
    implements GroupMessagesService{

}




