package com.example.notificationmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.example.notificationmanagement.mapper.UserRolesMapper;
import com.example.notificationmanagement.mapper.UsersInformationMapper;
import com.example.notificationmanagement.mapper.UsersMapper;
import com.example.notificationmanagement.pojo.UserRoles;
import com.example.notificationmanagement.pojo.Users;
import com.example.notificationmanagement.pojo.UsersInformation;
import com.example.notificationmanagement.pojo.UsersInformationAndactive;
import com.example.notificationmanagement.service.UsersInformationService;
import com.example.notificationmanagement.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yuzongyang
 * @description 针对表【users_information】的数据库操作Service实现
 * @createDate 2024-06-08 13:39:19
 */
@Service
public class UsersInformationServiceImpl extends ServiceImpl<UsersInformationMapper, UsersInformation>
        implements UsersInformationService{
    private List<UsersInformationAndactive> usersInformationList;

    @Autowired
    private UsersInformationMapper usersInformationMapper;
    @Autowired
    private UserRolesMapper userRolesMapper;
    @Autowired
    private UsersMapper usersMapper;


    @Override
    public List<UsersInformationAndactive> getAllInformation() {
        usersInformationList = usersInformationMapper.selectallusers();
        return usersInformationList;
    }
@Override
    public List<UsersInformationAndactive> searchUsersInformation(String searchQuery) {
        return usersInformationList.stream()
                .filter(user -> user.getUsername().contains(searchQuery))
                .collect(Collectors.toList());
    }
    @Override
    public void deleteUser(Integer userId) {
        usersInformationMapper.deleteById(userId);
        Db.removeById(userId, UserRoles.class);
        Db.removeById(userId,Users.class );
    }

    @Override
    public void resetpassword(int userid){
        String password= MD5Util.md5("123456");
        usersInformationMapper.resetPassword(userid,password);

    }
    @Override
     public UsersInformation selectuser(String username){
        QueryWrapper<UsersInformation> wrapper=new QueryWrapper<UsersInformation>()
                .select( "email","phone","address","username")
                .eq("username",username);

        UsersInformation usersInformation=usersInformationMapper.selectOne(wrapper);
        return  usersInformation;
    }

    @Override
    public void updateUserByUsername(UsersInformation updatedUser) {
        UpdateWrapper<UsersInformation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", updatedUser.getUsername());
        usersInformationMapper.update(updatedUser, updateWrapper);
    }
@Override
    public int updateUserActiveStatus(int userId, boolean isactive) {
        Users user=new Users();
        user.setIsactive(isactive);
        UpdateWrapper<Users> updateWrapper = new UpdateWrapper<Users>()
                .eq("userid",userId);
        return usersMapper.update(user,updateWrapper);
}



}




