package com.yicj.client.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessTokenBean implements Serializable {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private Long expires_in;
    private String scope;
}