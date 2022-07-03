package com.yicj.client;

import com.yicj.client.config.ThymeleafConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(ThymeleafConfig.class)
@SpringBootApplication
public class AuthClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthClientApplication.class, args) ;
    }
}
