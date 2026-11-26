package com.mdkj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRetry
@EnableScheduling
@MapperScan("com.mdkj.mapper")
@SpringBootApplication
public class ManageApp {
    public static void main(String[] args) {
        SpringApplication.run(ManageApp.class, args);
    }
}
