package com.example.notificationmanagement.service;

import com.example.notificationmanagement.pojo.UserRoles;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.notificationmanagement.pojo.Userpeimission;

import java.util.List;

/**
* @author Yuzongyang
* @description 针对表【user_roles】的数据库操作Service
* @createDate 2024-05-28 12:34:17
*/
public interface UserRolesService extends IService<UserRoles> {
    public boolean JudgeIdentity (String username);
    public String getrolename(String username);
    public List<Userpeimission> getUserPermissions(String role) ;

    public void updaterole(Integer userid,String rolename);

}
