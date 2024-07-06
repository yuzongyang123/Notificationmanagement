package com.example.notificationmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com/example/notificationmanagement/mapper")

public class NotificationmanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationmanagementApplication.class, args);
    }


}
