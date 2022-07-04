package com.yicj.client;

import com.yicj.client.aop.EnableAuthValidate;
import com.yicj.client.config.ThymeleafConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableAuthValidate(basePackage = "com.yicj.client")
@EnableConfigurationProperties(ThymeleafConfig.class)
@SpringBootApplication
public class AuthClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthClientApplication.class, args) ;
    }
}
