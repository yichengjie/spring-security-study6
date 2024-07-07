package com.yicj.client.common.configuration;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * AuthorizedUserFeignClientConfiguration
 * </p>
 *
 * @author yicj
 * @since 2024年07月07日 20:11
 */
public class UserFeignClientConfiguration {
    @Bean("userFeignClientConfiguration")
    public RequestInterceptor getOAuth2RequestInterceptor() {
        return (RequestTemplate template) -> {
            //todo  获取当前用户的token 传递给feign调用的服务
            String token = "token";
            template.header("Authorization", "Bearer " + token);
        };
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
