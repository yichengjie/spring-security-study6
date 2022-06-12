package com.yicj.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtTest {

    private final String secret = "!ISN!@#%" ;

    @Test
    public void encode(){
        Map<String,Object> map = new HashMap<>() ;
        map.put("headerKey1", "headerValue1") ;
        map.put("headerKey2", "headerValue2") ;
        // 获取日历对象
        Calendar instance = Calendar.getInstance();
        // 默认30秒过期
        instance.add(Calendar.MINUTE, 30);

        String token = JWT.create()
                .withHeader(map)
                .withClaim("userId",21) // payload
                .withClaim("username", "Garry") // playload
                .withExpiresAt(instance.getTime()) // 设置过期时间
                .sign(Algorithm.HMAC256(secret)) ; // 签名
        //
        log.info("token : {}", token);
    }

    @Test
    public void decode(){
        String token = "eyJoZWFkZXJLZXkyIjoiaGVhZGVyVmFsdWUyIiwidHlwIjoiSldUIiwiaGVhZGVyS2V5MSI6ImhlYWRlclZhbHVlMSIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE2NTUwNDIwMzYsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoiR2FycnkifQ.Kt8uBGO25u0A3ATEXIuc8nxbZZlxy1kAyu_3_XviPV0" ;
        JWTVerifier jwtVerifier =
                JWT.require(Algorithm.HMAC256(secret)).build() ;
        DecodedJWT verify = jwtVerifier.verify(token);
        log.info("userId : {}", verify.getClaim("userId").asInt());
        log.info("username: {}", verify.getClaim("username").asString());
    }
}
