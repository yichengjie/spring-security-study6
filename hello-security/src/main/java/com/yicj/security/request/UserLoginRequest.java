package com.yicj.security.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private String mobile ;
    private String password ;
    private String verifyCode ;
}
