package com.example.notificationmanagement.mapper;

import com.example.notificationmanagement.pojo.Organizations;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.notificationmanagement.pojo.UserOrganization;
import com.example.notificationmanagement.pojo.UsersInformation;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;

/**
* @author Yuzongyang
* @description 针对表【organizations】的数据库操作Mapper
* @createDate 2024-05-28 12:34:17
* @Entity com.example.notificationmanagement.pojo.Organizations
*/
@Mapper
public interface OrganizationsMapper extends BaseMapper<Organizations> {

    //查询所处组织
    @Select("SELECT DISTINCT user_organization.OrganizationID, organizations.OrganizationName, user_organization.IsNotifier  " +
            "FROM user_organization " +
            "JOIN organizations ON organizations.OrganizationID = user_organization.OrganizationID " +
            "WHERE userid = #{userid}")
    List<Organizations> selectLivingOrganizations(Integer userid);




    @Insert("INSERT INTO organizations (organizationname,createdby) VALUES (#{organizationname}, #{createdby})")
    int createorganization(@Param("organizationname") String organizationname, @Param("createdby") int createdby);

    @Update("UPDATE organizations SET organizationname = #{organizationname} WHERE OrganizationID = #{OrganizationID}")
    void updatename(@Param("OrganizationID") Integer OrganizationID, @Param("organizationname") String organizationname);

    @Delete("Delete  from organizations where OrganizationID = #{OrganizationID}")
    int deleteorganization(Serializable OrganizationID);
}