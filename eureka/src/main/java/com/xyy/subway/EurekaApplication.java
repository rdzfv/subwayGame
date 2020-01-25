package com.xyy.subway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author xyy
 * @date 2020/1/24 11:29
 * @description SpringBoot启动类
 */
@EnableEurekaServer // 让应用变为 Eureka Server
@SpringBootApplication
public class EurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}