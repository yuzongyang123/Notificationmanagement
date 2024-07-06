package com.example.notificationmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.example.notificationmanagement.mapper.UsersMapper;
import com.example.notificationmanagement.pojo.*;
import com.example.notificationmanagement.service.UserOrganizationService;
import com.example.notificationmanagement.mapper.UserOrganizationMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yuzongyang
 * @description 针对表【user_organization】的数据库操作Service实现
 * @createDate 2024-05-28 12:34:17
 */
@Service
public class UserOrganizationServiceImpl extends ServiceImpl<UserOrganizationMapper, UserOrganization>
        implements UserOrganizationService{
    @Autowired
    private UserOrganizationMapper userOrganizationMapper;

    @Override
    public List<UserOrganizationDto> selectByOrganizationId(Integer organizationId) {
        return userOrganizationMapper.selectMembersByOrganizationId(organizationId);
    }


    @Override
    public int addmember(Integer organizationId,String name){
        Users user=Db.lambdaQuery(Users.class)
                .select(Users::getUserid)
                .eq(Users::getUsername,name).one();
        if(user.getUserid()!=null) {
            int a = userOrganizationMapper.addmember(organizationId, user.getUserid());
            return a;
        }
        else{
        return 0;
        }
    }
    @Override
    public void deletemember(Integer organizationId, Integer userid) {
        userOrganizationMapper.deletemember(organizationId, userid);
    }

    @Override
    public int updateUserRole(Integer organizationId,Integer userid,Boolean isNotifier){
        int x=userOrganizationMapper.updateUserRole(organizationId,userid,isNotifier);
        return x;
    }


}






