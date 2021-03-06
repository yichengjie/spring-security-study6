package com.yicj.security.properties;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 认证相关配置项（注意这里的配置项一般都是加到配置文件上的）
 *
 * @author HuaDong
 * @since 2021/4/24 21:09
 */
@Component
@Data
public class SecurityProperties {

    /**
     * JWT令牌名称
     */
    private String tokenName = "Access-Token";

    /**
     * JWT令牌加密用的盐
     */
    private String tokenEncryptSalt = "MEI_CLOUD";

    /**
     * JWT令牌过期时间, 秒, 2592000=30天
     */
    private Long tokenExpireTimeInSecond = 2592000L;

    /**
     * JWT令牌刷新时间, tokenExpireTime - currentTime < tokenFreshInterval, 会重新刷新令牌
     */
    private Long tokenRefreshIntervalInSecond = 864000L;

    /**
     * 配置白名单（比如登录接口）
     */
    protected String[] permitUrls = new String[]{"/user/login", "/sentinel/**"};

    /**
     * 匿名访问的URL，即不用登录也可以访问（比如广告接口）
     */
    protected String[] anonymousUrls = new String[]{"/ad"};

}