package com.xyy.subway.artree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xyy
 * @date 2020/1/24 14:01
 * @description
 */
@EnableJpaAuditing
@EnableEurekaClient
@SpringBootApplication
@EnableSwagger2
public class TrueWorldApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrueWorldApplication.class, args);
    }
}
