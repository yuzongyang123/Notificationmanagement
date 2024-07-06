package com.example.notificationmanagement.pojo;

public class UsersInformationAndactive {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }



    private Integer id;
    private String username;
    private String address;
    private String phone;
    private String email;
    private Integer userid;

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean isActive) {
        IsActive = isActive;
    }

    private boolean IsActive;
}
