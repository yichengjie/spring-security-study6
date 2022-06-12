package com.yicj.security.filter;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson.JSON;
import com.yicj.security.request.UserLoginRequest;
import com.yicj.security.token.UserAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UserAuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public UserAuthenticationFilter() {
        super(new AntPathRequestMatcher("/user/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // todo 这里的逻辑主要有两个作用，一个是进行初步的校验，一个是组装待认证的Token，举几个例子
        //1. 微信授权登陆：客户端会传过来一些密串，这里逻辑主要解密这些加密串的数据获取unionId、openId、手机号及用户昵称头像等基本信息，
        // 然后组装Token传递给Provider进行下一步认证，如果这里报错直接就返回异常，不会进行下一步认证。

        //2. 手机短信验码登陆：这里主要验证短信验证码的正确性，然后组装Token传递给Provder进行下一步认证，如果短信验证码错误直接抛异常。

        //3. 账号密码图形验证码登陆：这里主要验证图形验证码的正确性，然后组装Token传递给Provider进行下一步认证，如果图形验证码错误直接抛异常。

        // ...
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8) ;
        String mobile = null, password = null, verifyCode = null ;
        if (StringUtils.hasText(body)){
            UserLoginRequest loginRequest = JSON.parseObject(body, UserLoginRequest.class);
            mobile = loginRequest.getMobile() ;
            password = loginRequest.getPassword() ;
            verifyCode = loginRequest.getVerifyCode() ;
        }
        // todo 这里验证图形验证码verifyCode是否正确
        UserAuthenticationToken token = new UserAuthenticationToken(mobile, password) ;
        // 这里进行下一步认证，会走到我们定义的UserAuthenticationProvider中
        return this.getAuthenticationManager().authenticate(token);
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.getAuthenticationManager(), "AuthenticationManager must be specified") ;
        Assert.notNull(this.getSuccessHandler(), "AuthenticationSuccessHandler must be specified") ;
        Assert.notNull(this.getFailureHandler(), "AuthenticationFailureHandler must be specified") ;
    }
}
