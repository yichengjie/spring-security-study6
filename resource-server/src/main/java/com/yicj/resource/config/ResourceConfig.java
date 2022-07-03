package com.yicj.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

//https://www.jianshu.com/p/a89773942aab
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private ResourceServerTokenServices tokenServices ;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 本资源服务的ID，用于在授权服务器那里验证是否存在这个ID的资源服务
        resources.resourceId("client")
                // 加载token规则
                .tokenServices(tokenServices)
                // 禁用session
                .stateless(true) ;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('read')")
                .and()
                .csrf().disable()
                // 禁用session,因为我们使用的token，session没有用途
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }

    /**
     * 定义校验token的规则，见下面第二小节描述
     * @return
     */
    @Bean
    public ResourceServerTokenServices tokenServices(){
        // 当授权服务器和资源服务器不在同一台机器时需要使用RemoteTokenService
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices() ;
        // 指定授权服务器校验token的端点
        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        // 资源服务器自己独有身份信息
        remoteTokenServices.setClientId("client");
        remoteTokenServices.setClientSecret("secret");
        return remoteTokenServices ;
    }
}
