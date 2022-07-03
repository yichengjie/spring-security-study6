package com.yicj.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager ;
    @Autowired
    private PasswordEncoder passwordEncoder ;
    @Autowired
    private TokenStore tokenStore ;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter ;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret(passwordEncoder.encode("secret"))
                .scopes("read", "write")
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(72000)
                .authorizedGrantTypes("authorization_code","password", "refresh_token")
                .authorities("user:view")
                .redirectUris("http://localhost:7070/auth/callback");

//        InMemoryClientDetailsService service = new InMemoryClientDetailsService();
//        BaseClientDetails cd = new BaseClientDetails();
//        cd.setClientId("client");
//        cd.setClientSecret("secret");
//        cd.setScope(Arrays.asList("read"));
//        cd.setAuthorizedGrantTypes(Arrays.asList("authorization_code","password", "refresh_token"));
//
//        Map<String, ClientDetails> map = new HashMap<>() ;
//        map.put("client", cd) ;
//        service.setClientDetailsStore(map);
//
//        clients.withClientDetails(service) ;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // curl -u client:secret http://localhost:8080/oauth/token_key
        security.checkTokenAccess("permitAll()")
                .tokenKeyAccess("isAuthenticated()")
                //.allowFormAuthenticationForClients()
        ;
    }


    /*@Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices() ;
        // 配置客户端详情服务，获取客户端信息
        services.setClientDetailsService(clientDetailsService);
        // 支持刷新令牌
        services.setSupportRefreshToken(true);
        // 配置令牌存储方式，此时用内存存储
        services.setTokenStore(tokenStore);
        //有效时间2小时
        services.setAccessTokenValiditySeconds(7200);
        // 刷新令牌有效期3天
        services.setRefreshTokenValiditySeconds(259200);
        return services ;
    }*/

}
