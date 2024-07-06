package com.example.notificationmanagement.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName notifications
 */
@TableName(value ="notifications")
@Data
public class Notifications implements Serializable {
    /**
     *
     */
    @TableField(exist = false)
    private String orgizationname;

    /**
     *
     */
    @TableField(value = "Title")
    private String title;

    /**
     *
     */
    @TableField(value = "Content")
    private String content;

    /**
     *
     */
    @TableField(value = "ImageUrl")
    private String imageUrl; // 增加这个字段以保存图片路径

    /**
     *
     */
    @TableField(value = "Attachments")
    private String attachments;

    /**
     *
     */
    @TableField(value = "CreatedBy")
    private Integer createdby;

    /**
     *
     */
    @TableField(value = "CreatedAt")
    private Date createdat;

    /**
     *
     */
    @TableField(value = "OrganizationID")
    private Integer organizationid;


    /**
     *
     */
    @TableField(value = "IsActive")
    private Integer isactive;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;




}
