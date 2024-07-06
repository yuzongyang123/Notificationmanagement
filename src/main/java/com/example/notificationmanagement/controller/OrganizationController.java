package com.example.notificationmanagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.example.notificationmanagement.mapper.UserOrganizationMapper;
import com.example.notificationmanagement.mapper.UsersMapper;
import com.example.notificationmanagement.pojo.Organizations;
import com.example.notificationmanagement.pojo.UserOrganization;
import com.example.notificationmanagement.service.OrganizationsService;
import com.example.notificationmanagement.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(tags = "组织管理")

public class OrganizationController {

    @Autowired
    private OrganizationsService organizationsService;

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private UserOrganizationMapper userOrganizationMapper;
    private Integer userId;

    //获取创建的组织
    @GetMapping("/getorganization")
    public List<Organizations> getorganization(HttpServletRequest request) {
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

        if (token != null) {
            Claims claims = JwtUtil.parseToken(token);
            String username = claims.getSubject();

            if (username != null) {
                userId = usersMapper.findIdByUsername(username);
                if (userId != null) {
                    return organizationsService.getAllorganization(userId);
                }
            }
        }

        return null;
    }

    @PostMapping("/createOrganization")
    public boolean createOrganization(@RequestBody Organizations organization){
    String organizationname=organization.getOrganizationname();
    return organizationsService.CreateOrganization(organizationname,userId);
    }

    @PutMapping("/updatename/{organizationid}")
    public void updatename(@PathVariable("organizationid") Integer organizationid,@RequestBody Organizations organization) {
        organizationsService.updatename(organizationid,organization);


    }

    @DeleteMapping("/deleteorganization/{organizationid}")
    public  void deleteorganization(@PathVariable("organizationid") Integer organizationid){
        organizationsService.deleteorganization(organizationid);
    }

    //获取所在组织

    @GetMapping("/getliving")
    public List<Organizations> getliving(HttpServletRequest request){

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

        if (token != null) {
            Claims claims = JwtUtil.parseToken(token);
            String username = claims.getSubject();

            if (username != null) {
                userId = usersMapper.findIdByUsername(username);
                if (userId != null) {
                    return organizationsService.selectLivingOrganizations(userId);
                }
            }
        }

        return null;
    }

    @DeleteMapping("/leaveOrganization")
    public Integer leaveOrganization(@RequestParam("organizationid") int organizationid,HttpServletRequest request){
        String username=Getusername(request);
        userId = usersMapper.findIdByUsername(username);

        QueryWrapper<UserOrganization> wrapper=new QueryWrapper<UserOrganization>()
                .eq("organizationid",organizationid)
                .eq("userid",userId);

        int isok = userOrganizationMapper.delete(wrapper);

            return isok;


    }




    public static String Getusername(HttpServletRequest request) {
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
        if (token != null) {
            Claims claims = JwtUtil.parseToken(token);
            String username = claims.getSubject();
            return username;
        } else
            return null;
    }

}
