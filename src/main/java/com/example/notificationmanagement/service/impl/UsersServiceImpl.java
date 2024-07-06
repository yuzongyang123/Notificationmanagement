package com.example.notificationmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.notificationmanagement.exception.UsernameAlreadyExistsException;
import com.example.notificationmanagement.pojo.Users;
import com.example.notificationmanagement.service.UsersService;
import com.example.notificationmanagement.mapper.UsersMapper;
import com.example.notificationmanagement.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean authenticate(String username, String password) {
        QueryWrapper<Users> wrapper = new QueryWrapper<Users>()
                .select("password", "isactive")
                .eq("username", username);

        Users user = usersMapper.selectOne(wrapper);
        if (user != null) {
            String storedPassword = user.getPassword();
            boolean isActive = user.getIsactive();
            if (storedPassword != null && isActive) {
                // 比较存储的MD5密码和输入密码的MD5值
                return storedPassword.equals(MD5Util.md5(password));
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public int register(String username, String password) {
        // 对密码进行MD5哈希处理
        try {
            String hashedPassword = MD5Util.md5(password);
            // 创建用户
            Users user = new Users();
            user.setUsername(username);
            user.setPassword(hashedPassword);

            int result = usersMapper.insert(user);

            if (result > 0) {
                // 在 Redis 中初始化 tokenVersion
                redisTemplate.opsForValue().set(username + "_tokenVersion", 1);
            }

            return result;
        } catch (DataIntegrityViolationException e) {
            // Assuming the exception is due to a unique constraint violation on the username
            throw new UsernameAlreadyExistsException("用户名已注册");
        }
    }

    @Override
    public void updateTokenVersion(String username) {
        Integer tokenVersion = (Integer) redisTemplate.opsForValue().get(username + "_tokenVersion");
        if (tokenVersion != null) {
            tokenVersion += 1;
            redisTemplate.opsForValue().set(username + "_tokenVersion", tokenVersion);
        } else {
            redisTemplate.opsForValue().set(username + "_tokenVersion", 1);
        }
    }

    @Override
    public int getTokenVersion(String username) {
        Integer tokenVersion = (Integer) redisTemplate.opsForValue().get(username + "_tokenVersion");
        return tokenVersion != null ? tokenVersion : -1;
    }

    @Override
    public void logout(String username) {
        updateTokenVersion(username);
    }
}
