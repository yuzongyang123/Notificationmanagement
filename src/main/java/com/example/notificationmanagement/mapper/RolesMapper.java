package com.example.notificationmanagement.mapper;

import com.example.notificationmanagement.pojo.Roles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
/**
* @author Yuzongyang
* @description 针对表【roles】的数据库操作Mapper
* @createDate 2024-05-28 12:34:17
* @Entity com.example.notificationmanagement.pojo.Roles
*/
public interface RolesMapper extends BaseMapper<Roles> {
}




