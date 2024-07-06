package com.example.notificationmanagement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.notificationmanagement.pojo.UserRoles;
import com.example.notificationmanagement.pojo.Userpeimission;
import com.example.notificationmanagement.service.UserRolesService;
import com.example.notificationmanagement.mapper.UserRolesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Yuzongyang
* @description 针对表【user_roles】的数据库操作Service实现
* @createDate 2024-05-28 12:34:17
*/
@Service
public class UserRolesServiceImpl extends ServiceImpl<UserRolesMapper, UserRoles>
    implements UserRolesService {
    @Autowired
    UserRolesMapper userRolesMapper;

    @Override
    public boolean JudgeIdentity(String username) {
        int role = userRolesMapper.getRole(username);
        System.out.println("Checking permissions for user:" + username);

        if (role == 1 || role == 2)
            return true;
        else
            return false;


    }

    @Override
    public String getrolename(String username) {
        int role = userRolesMapper.getRole(username);
        if (role == 1)
            return "超级管理员max";
        else if (role == 2)
            return "超级管理员";
        else if (role == 4)
            return "组织管理者";
        else if (role == 5)
            return "通知发布者";
        else
            return "普通用户";


    }

    @Override
    public List<Userpeimission> getUserPermissions(String role) {
        if ("超级管理员max".equals(role)) {
            return userRolesMapper.getAllUsersExceptMax();
        } else if ("超级管理员".equals(role)) {
            return userRolesMapper.getAllUsersExceptMaxAndSuperAdmin();

        }
        else
            return null;
    }

    @Override
    public void updaterole(Integer userid,String rolename){
        if(rolename.equals("超级管理员"))
            userRolesMapper.updaterole(userid,2);
        else if (rolename.equals("普通用户")){
            userRolesMapper.updaterole(userid,3);
        }
        else if (rolename.equals("组织管理员")){
            userRolesMapper.updaterole(userid,4);
        }
    }
}




