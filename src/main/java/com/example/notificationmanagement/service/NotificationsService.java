package com.example.notificationmanagement.service;

import com.example.notificationmanagement.pojo.Notifications;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.notificationmanagement.pojo.NotifocationOrganization;
import com.example.notificationmanagement.pojo.Organization;
import com.example.notificationmanagement.pojo.Organizations;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author Yuzongyang
* @description 针对表【notifications】的数据库操作Service
* @createDate 2024-05-28 12:34:17
*/
public interface NotificationsService extends IService<Notifications> {
    public List<Organization> getNotifierOrganizations(String name);
    public int sendNotification(Integer organizationId, String title, String content, MultipartFile image, MultipartFile attachment,Integer createdby) ;
    public List<NotifocationOrganization> findNotificationsByOrganizationIds(Integer userid);

}
