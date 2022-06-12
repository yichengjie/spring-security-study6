package com.yicj.security.config;

import com.yicj.security.configurer.JwtLoginConfigurer;
import com.yicj.security.configurer.SecurityConfig;
import com.yicj.security.configurer.UserLoginConfigurer;
import com.yicj.security.provider.JwtAuthenticationProvider;
import com.yicj.security.provider.UserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityConfig securityConfig ;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // 配置白名单（比如登录接口）
                .antMatchers(securityConfig.getPermitUrls()).permitAll()
                // 匿名访问的url，即不用登录也可以反问（比如广告接口）
                .antMatchers(securityConfig.getAnonymousUrls()).permitAll()
                // 买家接口需要"RULE_BUYER"角色才能访问
                .antMatchers("/buyer/**").hasRole("BUYER")
                // 其他任何请求满足rbacService.hasPermission()方法返回true时，能访问
                .anyRequest().access("@rbacService.hasPermission(request,authentication)")
                // 其他url一律拒绝
                //.anyRequest().denyAll()
                .and()
                //禁用跨站点伪造请求
                .csrf().disable()
                // 启用跨域资源共享
                .cors()
                .and()
                // 添加请求头
                .headers().addHeaderWriter(
                        new StaticHeadersWriter(Collections.singletonList(
                                new Header("Access-control-Allow-Origin", "*")
                        ))
                )
                .and()
                //自定义登录过滤器，不同的登录方式创建不同登录过滤器，一样的配置方式
                .apply(new UserLoginConfigurer<>(securityConfig))
                .and()
                // 自定义的Jwt令牌认证过滤器
                .apply(new JwtLoginConfigurer<>(securityConfig))
                .and()
                // 退出登录
                .logout()
                // 退出登录处理器
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .and()
                // 禁用Session会话机制（我们这个demo用的是jwt令牌的方式）
                .sessionManagement().disable()
                // 禁用SecurityContext，这个配置器实际上认证信息会存在Session中，但是我们并不使用session机制，所以禁用
                .securityContext().disable() ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userAuthenticationProvider())
                .authenticationProvider(jwtAuthenticationProvider());
    }

    @Bean
    protected AuthenticationProvider userAuthenticationProvider() throws Exception {
        return new UserAuthenticationProvider();
    }

    @Bean
    protected AuthenticationProvider jwtAuthenticationProvider() throws Exception {
        return new JwtAuthenticationProvider(securityConfig);
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "HEAD", "DELETE", "PUT", "OPTION"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
