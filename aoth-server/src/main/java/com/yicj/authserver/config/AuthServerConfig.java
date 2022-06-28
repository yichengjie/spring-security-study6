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

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager ;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret(passwordEncoder.encode("secret"))
                .scopes("read", "write")
                .authorizedGrantTypes("authorization_code","password", "refresh_token")
                .authorities("user:view")
                .redirectUris("http://localhost:8080/oauth/callback");

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
        endpoints.authenticationManager(authenticationManager) ;
    }
}
