package com.yicj.security.provider;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yicj.security.properties.SecurityProperties;
import com.yicj.security.dto.JwtUserLoginDTO;
import com.yicj.security.exception.LoginAuthenticationException;
import com.yicj.security.token.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Calendar;
import java.util.Collections;

/**
 * JWT认证 Provider
 *
 * @author HuaDong
 * @since 2021/4/26 21:35
 */
public class JwtAuthenticationProvider implements AuthenticationProvider{

	private SecurityProperties securityConfig;

	public JwtAuthenticationProvider(SecurityProperties securityConfig) {
		this.securityConfig = securityConfig;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		DecodedJWT jwt = ((JwtAuthenticationToken) authentication).getToken();
		// 令牌过期
		if(jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
			throw LoginAuthenticationException.JWT_EXPIRED;
		}

		try {
			// 校验令牌的合法性
			Algorithm algorithm = Algorithm.HMAC256(securityConfig.getTokenEncryptSalt());
			JwtUserLoginDTO loginResultDTO = JwtUserLoginDTO.fromDecodeJWT(jwt, algorithm);
			return new JwtAuthenticationToken(loginResultDTO, jwt,
					Collections.singletonList(new SimpleGrantedAuthority(loginResultDTO.getRoleName())));
        } catch (Exception e) {
            throw new BadCredentialsException("JWT token verify fail", e);
        }
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JwtAuthenticationToken.class);
	}

}