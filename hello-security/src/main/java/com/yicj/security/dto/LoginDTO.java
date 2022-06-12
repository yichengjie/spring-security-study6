package com.yicj.security.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDTO implements Serializable {
    private String userType ;
    private String username ;
}
