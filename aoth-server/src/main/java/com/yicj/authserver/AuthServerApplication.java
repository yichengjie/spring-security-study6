package com.yicj.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) {
        //https://blog.csdn.net/qq_41071876/article/details/122389002
        // http://localhost:8080/oauth/authorize?response_type=code&client_id=client&scope=read
        SpringApplication.run(AuthServerApplication.class, args) ;
    }
}
