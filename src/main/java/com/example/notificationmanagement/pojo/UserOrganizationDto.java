package com.example.notificationmanagement.pojo;

public class    UserOrganizationDto {
    private Integer userid;
    private String username;
    private Boolean isNotifier;

    // Getters and setters
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIsNotifier() {
        return isNotifier;
    }

    public void setIsNotifier(Boolean isNotifier) {
        this.isNotifier = isNotifier;
    }
}
