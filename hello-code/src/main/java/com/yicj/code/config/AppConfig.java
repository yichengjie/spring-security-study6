package com.yicj.code.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class AppConfig extends WebSecurityConfigurerAdapter {

    //@Autowired
    //private AuthenticationProvider authenticationProvider ;


    private ClientRegistration clientRegistration(){
//        return CommonOAuth2Provider.GITHUB
//                .getBuilder("github")
//                .clientId("clientId-value")
//                .clientSecret("clientSecret-value")
//                .build() ;
        ClientRegistration cr = ClientRegistration.withRegistrationId("github")
                .clientId("client-value")
                .clientSecret("clientSecurt-value")
                .scope(new String[]{"read:user"})
                .authorizationUri("https://github.com/login/oauth/authorize")
                .tokenUri("https://github.com/login.oauth.access_token")
                .userInfoUri("https://api.github.com/user")
                .userNameAttributeName("id")
                .clientName("Github")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUriTemplate("{baseUrl}/{action}/oauth2/code/{registrationId}")
                .build() ;
        return cr ;
    }

    public ClientRegistrationRepository clientRegistrationRepository(){
        ClientRegistration clientRegistration = clientRegistration();
        return new InMemoryClientRegistrationRepository(clientRegistration) ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.formLogin() ;
        http.oauth2Login(c -> c.clientRegistrationRepository(clientRegistrationRepository())) ;
        http.authorizeRequests()
                .anyRequest()
                .authenticated() ;
                //.and()
                //.exceptionHandling().authenticationEntryPoint()

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        //auth.authenticationProvider(authenticationProvider) ;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails user1 = User.withUsername("john")
                .password("123456")
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.withUsername("jane")
                .password("123456")
                .roles("MANAGER")
                .build();

        manager.createUser(user1);
        manager.createUser(user2);

        return manager ;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance() ;
    }
}
