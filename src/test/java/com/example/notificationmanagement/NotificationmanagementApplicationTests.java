package com.example.notificationmanagement;

import com.example.notificationmanagement.pojo.Users;
import com.example.notificationmanagement.util.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.auth.*;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import java.io.ByteArrayInputStream;

public class NotificationmanagementApplicationTests {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("com.example.MyClass");
        System.out.println(clazz);


    }
}