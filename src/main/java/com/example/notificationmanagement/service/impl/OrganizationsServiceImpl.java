package com.example.notificationmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.notificationmanagement.mapper.UserOrganizationMapper;
import com.example.notificationmanagement.pojo.Organizations;
import com.example.notificationmanagement.pojo.UserOrganization;
import com.example.notificationmanagement.pojo.UsersInformation;
import com.example.notificationmanagement.service.OrganizationsService;
import com.example.notificationmanagement.mapper.OrganizationsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Yuzongyang
* @description 针对表【organizations】的数据库操作Service实现
* @createDate 2024-05-28 12:34:17
*/
@Service
public class OrganizationsServiceImpl extends ServiceImpl<OrganizationsMapper, Organizations>
    implements OrganizationsService{
    @Autowired
    OrganizationsMapper organizationsMapper;
    @Autowired
    UserOrganizationMapper userOrganizationMapper;

    //查询创建的组织

    @Override
    public List<Organizations> getAllorganization(Integer userid) {
        QueryWrapper<Organizations> wrapper=new QueryWrapper<Organizations>()
                .select("OrganizationID","OrganizationName")
                .eq("CreatedBy",userid);
        return organizationsMapper.selectList(wrapper);
    }

    @Override
    public Boolean CreateOrganization(String username,Integer userid){
        int x=organizationsMapper.createorganization(username,userid);
        if(x==1)
            return true;
        else
            return false;
    }

    @Override
    public void updatename(Integer organizationid,Organizations organization){
        UpdateWrapper<Organizations> wrapper=new UpdateWrapper<Organizations>()
                .eq("OrganizationID",organizationid);
        organizationsMapper.update(organization,wrapper);
    }
    public static <T> boolean remove(Class<T> entityClass, AbstractWrapper<T, ?, ?> queryWrapper) {
        return SqlHelper.execute(entityClass, (baseMapper) -> {
            return SqlHelper.retBool(baseMapper.delete(queryWrapper));
        });
    }


    @Override
    public  void deleteorganization(Integer organizationid){
        QueryWrapper<UserOrganization> wrapper=new QueryWrapper<UserOrganization>()
                .eq("organizationid",organizationid);
        remove(UserOrganization.class,wrapper);
        userOrganizationMapper.deleteuser_organization(organizationid);
        organizationsMapper.deleteorganization(organizationid);
    }

    @Override
    public List<Organizations> selectLivingOrganizations(Integer userid) {
        List<Organizations> organizations = organizationsMapper.selectLivingOrganizations(userid);
        if (organizations.isEmpty()) {
            return null;
        }
        return organizations;
    }


}




