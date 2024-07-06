package com.example.notificationmanagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.notificationmanagement.mapper.UsersMapper;
import com.example.notificationmanagement.pojo.Notifications;
import com.example.notificationmanagement.pojo.NotifocationOrganization;
import com.example.notificationmanagement.pojo.Organization;
import com.example.notificationmanagement.service.NotificationsService;
import com.example.notificationmanagement.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Notifications")
public class NotificationsController {
    @Autowired
    private NotificationsService notificationsService;
    @Autowired
    private UsersMapper usersMapper;

    @GetMapping("/getNotifierOrganizations")
    public List<Organization> getNotifierOrganizations(HttpServletRequest request) {
        String username = Getusername(request);
        return notificationsService.getNotifierOrganizations(username);
    }

    @PostMapping("/sendNotification")
    public ResponseEntity<Map<String, Object>> sendNotification(
            @RequestParam("organizationId") Integer organizationId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment,
            HttpServletRequest request) {
        String username = Getusername(request);
        Integer userId = usersMapper.findIdByUsername(username);
        LambdaQueryWrapper<Notifications> wrapper = new LambdaQueryWrapper<Notifications>()
                .eq(Notifications::getOrganizationid, organizationId)
                .eq(Notifications::getTitle, title)
                .eq(Notifications::getCreatedby, userId);

        Map<String, Object> response = new HashMap<>();

        // 检查通知是否已存在
        if (notificationsService.getOne(wrapper) != null) {
            // 如果通知已存在，返回 409 Conflict
            response.put("status", HttpStatus.CONFLICT.value());
            response.put("message", "通知已存在");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // 如果通知不存在，继续发送通知
        int result = notificationsService.sendNotification(organizationId, title, content, image, attachment, userId);
        response.put("status", HttpStatus.OK.value());
        response.put("result", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getNotifications")
    public List<NotifocationOrganization> getNotifications(HttpServletRequest request) {
        String username = Getusername(request);
        Integer userId = usersMapper.findIdByUsername(username);
        List<NotifocationOrganization> notifocationOrganizations = notificationsService.findNotificationsByOrganizationIds(userId);
        notifocationOrganizations.stream()
                .forEach(notificationOrganization -> notificationOrganization.setNofiter(notificationOrganization.getCreatedBy() == userId));
        return notifocationOrganizations;
    }

    @DeleteMapping("/deleteNotification")
    public ResponseEntity<Map<String, Object>> deleteNotification(
            @RequestParam("createdBy") int createdBy,
            @RequestParam("title") String title,
            @RequestParam("organizationId") int organizationId) {
        QueryWrapper<Notifications> wrapper = new QueryWrapper<Notifications>()
                .eq("createdBy", createdBy)
                .eq("title", title)
                .eq("organizationid", organizationId);
        boolean success = notificationsService.remove(wrapper);
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("status", HttpStatus.OK.value());
            response.put("message", "删除通知成功");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", HttpStatus.UNAUTHORIZED.value());
            response.put("message", "删除失败");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    public static String Getusername(HttpServletRequest request) {
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
        if (token != null) {
            Claims claims = JwtUtil.parseToken(token);
            String username = claims.getSubject();
            return username;
        } else
            return null;
    }
}
