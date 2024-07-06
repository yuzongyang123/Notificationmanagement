package com.example.notificationmanagement.controller;

import com.example.notificationmanagement.pojo.Userpeimission;
import com.example.notificationmanagement.pojo.Users;
import com.example.notificationmanagement.service.UserRolesService;
import com.example.notificationmanagement.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "用户权限管理")

public class PermissionsController {

    @Autowired
    private UserRolesService userRolesService;

    @GetMapping("/getpermission")            //在进入主页时获取权限
    public ResponseEntity<UserRoleDTO> getPermission(HttpServletRequest request) {
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {    //获取cookie中的token
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null) {
            try {
                Claims claims = JwtUtil.parseToken(token);
                String username = claims.getSubject();
                boolean isAdmin = userRolesService.JudgeIdentity(username);
                String role = userRolesService.getrolename(username);
                UserRoleDTO userRoleDTO = new UserRoleDTO(isAdmin, role);
                return ResponseEntity.ok(userRoleDTO);

            } catch (JwtException e) {
                e.printStackTrace(); // 打印 JWT 解析错误
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            } catch (Exception e) {
                e.printStackTrace(); // 打印其他异常
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @GetMapping("/getUserPermissions")
    public UserPermissionsResponse getUserPermissions(HttpServletRequest request) {
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
        String role = claims.get("role", String.class); // 获取用户角色
        String username = claims.getSubject();
        String role1 = userRolesService.getrolename(username);
        if(role1.equals("超级管理员max")) {
            List<Userpeimission> permissions = userRolesService.getUserPermissions(role1);
            return new UserPermissionsResponse(permissions, true);
        }
        else{
            List<Userpeimission> permissions = userRolesService.getUserPermissions(role1);
            return new UserPermissionsResponse(permissions, false);
        }

    }

    @PutMapping("/updateuser/{userid}")
    public void updateUser(@PathVariable Integer userid,@RequestBody Userpeimission request) {
        String rolename=request.getRolename();
        userRolesService.updaterole(userid,rolename);
    }









    public static class UserRoleDTO {
        private boolean isAdmin;
        private String role;

        public UserRoleDTO(boolean isAdmin, String role) {
            this.isAdmin = isAdmin;
            this.role = role;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }


    public class UserPermissionsResponse {
        private List<Userpeimission> permissions;
        private boolean isadmin;

        public boolean isIsadmin() {
            return isadmin;
        }

        public void setIsadmin(boolean isadmin) {
            this.isadmin = isadmin;
        }


        public UserPermissionsResponse(List<Userpeimission> permissions, boolean isadmin) {
            this.permissions = permissions;
            this.isadmin = isadmin;
        }

        // Getters and setters
        public List<Userpeimission> getPermissions() {
            return permissions;
        }

        public void setPermissions(List<Userpeimission> permissions) {
            this.permissions = permissions;
        }


    }

}
