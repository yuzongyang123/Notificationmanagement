package com.example.notificationmanagement.service;

import com.example.notificationmanagement.pojo.Organizations;
import com.example.notificationmanagement.pojo.UserOrganization;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.notificationmanagement.pojo.UserOrganizationDto;
import com.example.notificationmanagement.pojo.Users;

import java.util.List;

/**
* @author Yuzongyang
* @description 针对表【user_organization】的数据库操作Service
* @createDate 2024-05-28 12:34:17
*/
public interface UserOrganizationService extends IService<UserOrganization> {
    public List<UserOrganizationDto> selectByOrganizationId(Integer organizationId);
    public int addmember(Integer organizationId,String name);
    public void deletemember(Integer orgabizationid,Integer userid);

    public int updateUserRole(Integer organizationId,Integer userid,Boolean isNotifier);
}
