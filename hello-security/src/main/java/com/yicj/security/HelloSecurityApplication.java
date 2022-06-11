package com.yicj.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class HelloSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSecurityApplication.class, args) ;
    }
}
