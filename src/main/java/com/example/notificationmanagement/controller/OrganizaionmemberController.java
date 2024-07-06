package com.example.notificationmanagement.controller;
import com.example.notificationmanagement.pojo.UpdateMemberRoleRequest;
import com.example.notificationmanagement.pojo.UserOrganization;
import com.example.notificationmanagement.pojo.UserOrganizationDto;
import com.example.notificationmanagement.pojo.Users;

import com.example.notificationmanagement.service.UserOrganizationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "组织成员管理")

public class OrganizaionmemberController {

    @Autowired
    UserOrganizationService userOrganizationService;

    @GetMapping("/showmembers/{organizationId}")
    public List<UserOrganizationDto> showmembers(@PathVariable("organizationId") Integer organizationId) {
        return userOrganizationService.selectByOrganizationId(organizationId);

    }

    @PostMapping("/addmember/{organizationId}")
    public int  addmember(@PathVariable("organizationId") Integer organizationId, @RequestBody NewMemberRequest newMemberRequest) {
        String name = newMemberRequest.getName();
        int a=userOrganizationService.addmember(organizationId, name);
        if(a==1)
            return 1;
        else
            return 0;


    }

    @DeleteMapping("/deletemember/{organizationId}/{memberId}")
    public void deletemember(@PathVariable("organizationId") Integer organizationId,@PathVariable("memberId") Integer memberId){
    userOrganizationService.deletemember(organizationId,memberId);

    }

    @PutMapping("updateMemberRole/{organizationId}")
    public int  updateMemberRole(@PathVariable("organizationId") Integer organizationId,@RequestBody UpdateMemberRoleRequest request){
        Integer userid=request.getUserid();
        Boolean isNotifier=request.getIsNotifier();
        return userOrganizationService.updateUserRole(organizationId,userid,isNotifier);
    }


    public static class NewMemberRequest {
        private String name;

        // Getter 和 Setter 方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }
}
