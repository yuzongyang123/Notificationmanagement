package com.example.notificationmanagement.pojo;

import java.util.Date;

public class NotifocationOrganization {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getAttachments() {
        return Attachments;
    }

    public void setAttachments(String attachments) {
        Attachments = attachments;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public Integer getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(Integer createdBy) {
        CreatedBy = createdBy;
    }

    public Integer getOrganizationID() {
        return OrganizationID;
    }

    public void setOrganizationID(Integer organizationID) {
        OrganizationID = organizationID;
    }

    private String title;
    private String content;
    private String ImageUrl;
    private String Attachments;

    public boolean isNofiter() {
        return isNofiter;
    }

    public void setNofiter(boolean nofiter) {
        isNofiter = nofiter;
    }

    private  boolean isNofiter;


    public String getOrganizationname() {
        return organizationname;
    }

    public void setOrganizationname(String organizationname) {
        this.organizationname = organizationname;
    }

    private String organizationname;
    private Date CreatedAt;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    private Integer CreatedBy,OrganizationID;
}
