package com.example.notificationmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.notificationmanagement.mapper.NotificationsMapper;
import com.example.notificationmanagement.pojo.Notifications;
import com.example.notificationmanagement.pojo.NotifocationOrganization;
import com.example.notificationmanagement.pojo.Organization;
import com.example.notificationmanagement.service.NotificationsService;
import com.example.notificationmanagement.util.AlyOSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class NotificationsServiceImpl extends ServiceImpl<NotificationsMapper, Notifications> implements NotificationsService {

    @Autowired
    private NotificationsMapper notificationsMapper;

    @Override
    public List<Organization> getNotifierOrganizations(String name) {
        return notificationsMapper.getNotifierOrganizations(name);
    }

    @Override
    public int sendNotification(Integer organizationId, String title, String content, MultipartFile image, MultipartFile attachment, Integer createdby) {
        String imageUrl = uploadFileToOSS(image);
        String attachmentUrl = uploadFileToOSS(attachment);

        Notifications notification = new Notifications();
        notification.setOrganizationid(organizationId);
        notification.setTitle(title);
        notification.setContent(content);
        notification.setImageUrl(imageUrl);
        notification.setAttachments(attachmentUrl);
        notification.setCreatedby(createdby);
        notificationsMapper.insert(notification);
        return 1;
    }

    private String uploadFileToOSS(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try (InputStream inputStream = file.getInputStream()) {
                String originalFilename = file.getOriginalFilename();
                // 生成一个唯一的文件名，可以使用时间戳或 UUID
                String objectName = System.currentTimeMillis() + "_" + originalFilename;
                String fileUrl = AlyOSS.uploadfile(objectName, inputStream);
                System.out.println("File uploaded to OSS: " + fileUrl); // 添加日志输出
                return fileUrl;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("OSS Upload failed: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("File is null or empty"); // 添加日志输出
        }
        return null;
    }

    @Override
    public List<NotifocationOrganization> findNotificationsByOrganizationIds(Integer userid) {

        List<Integer> organizationIds = notificationsMapper.findOrganizationIdsByUserId(userid);
        return notificationsMapper.findNotificationsByOrganizationIds(organizationIds);
    }
}
