package com.yicj.hello.config;

import com.yicj.hello.filter.JsonUsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter () throws Exception {
        JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter() ;
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter ;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().authenticated().and()
            .formLogin().and()
            .httpBasic().and()
            .csrf().disable();
        http.addFilterBefore(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    //@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
