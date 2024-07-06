package com.example.notificationmanagement.pojo;

public class UpdateMemberRoleRequest {
    private Integer userid;
    private Boolean isNotifier;

    // Getters and setters
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Boolean getIsNotifier() {
        return isNotifier;
    }

    public void setIsNotifier(Boolean isNotifier) {
        this.isNotifier = isNotifier;
    }
}
