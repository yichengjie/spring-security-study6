package com.yicj.authserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
public class TokenConfig {

    @Value("${jwt.key}")
    private String SIGNING_KEY;
    @Value("${jwk.password}")
    private String password ;
    @Value("${jwk.privateKey}")
    private String privateKey ;
    @Value("${jwk.alias}")
    private String alias ;

    // 令牌存储策略
    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        //JWT令牌存储方案
        return new JwtTokenStore(jwtAccessTokenConverter);
    }
    /***
     * 定义JJwtAccessTokenConverter
     * @return
     */
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey(SIGNING_KEY); //对称秘钥，资源服务器使用该秘钥来验证
//        return converter;
//    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource(privateKey), password.toCharArray()
        ) ;
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(alias));
        return converter ;
    }
}
