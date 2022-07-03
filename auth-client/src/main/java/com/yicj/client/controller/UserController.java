package com.yicj.client.controller;

import com.yicj.client.model.AccessTokenBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
public class UserController {

    private static final String GET_ACCESS_TOKEN_URL = "http://localhost:8080/oauth/token";
    private static final String GET_USER_NAME_URL = "http://localhost:9090/user/getCurrentUser";

    @Autowired
    private RestTemplate restTemplate ;


    // 登录首页，供用户选择使用授权服务进行登录
    @GetMapping("/login")
    public String getUserName(){
        return "index" ;
    }


    private AccessTokenBean getAccessToken(String authorizationCode){
        //client_id={clientId}&client_secret={client_secret}&grant_type={grant_type}&redirect_uri={redirect_uri}&code={code}
        // 组装参数
        HttpHeaders header = new HttpHeaders() ;
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.setBasicAuth("client", "secret");

        MultiValueMap<String,String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("grant_type", "authorization_code"); ;
        paramMap.add("redirect_uri", "http://localhost:7070/auth/callback"); ;
        paramMap.add("code", authorizationCode); ;
        HttpEntity<AccessTokenBean> httpEntity = new HttpEntity(paramMap, header) ;
        // 使用授权码再次访问授权服务器获取访问token
        return restTemplate.postForObject(GET_ACCESS_TOKEN_URL, httpEntity, AccessTokenBean.class) ;
    }

    private String getUserInfo(String accessToken){
        HttpHeaders header = new HttpHeaders() ;
        // 指定json格式
        header.setContentType(MediaType.APPLICATION_JSON);
        header.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, header) ;
        ResponseEntity<String> exchange = restTemplate.exchange(GET_USER_NAME_URL, HttpMethod.GET, httpEntity, String.class);
        return exchange.getBody();
    }

    @GetMapping("/auth/callback")
    public String getAuthorizationCode(
            @RequestParam("code") String authorizationCode,
            Model model){
        log.info("授权码是：{}", authorizationCode);
        AccessTokenBean accessTokenBean = this.getAccessToken(authorizationCode);
        if (ObjectUtils.isEmpty(accessTokenBean) ||
                ObjectUtils.isEmpty(accessTokenBean.getAccess_token())){
            log.error("未成功获取访问令牌！");
            return null ;
        }
        String accessToken = accessTokenBean.getAccess_token();
        log.info("访问令牌是：{}", accessToken);
        // 使用访问令牌访问资源服务器获取用户信息
        String userName = this.getUserInfo(accessToken);
        log.info("username : {}", userName);
        // 设置用户信息并渲染页面
        model.addAttribute("userName", userName) ;
        return "hello" ;
    }

}
