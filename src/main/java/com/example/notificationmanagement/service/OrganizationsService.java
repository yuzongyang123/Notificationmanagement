package com.example.notificationmanagement.service;

import com.example.notificationmanagement.pojo.Organizations;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.notificationmanagement.pojo.UsersInformation;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
* @author Yuzongyang
* @description 针对表【organizations】的数据库操作Service
* @createDate 2024-05-28 12:34:17
*/
public interface OrganizationsService extends IService<Organizations> {
    public List<Organizations> getAllorganization(Integer userid) ;
    public Boolean CreateOrganization(String username,Integer userid);
    public void updatename(Integer organizationid,Organizations organization);

    public  void deleteorganization(Integer organizationid);
    public  List<Organizations> selectLivingOrganizations(Integer userid);

}
