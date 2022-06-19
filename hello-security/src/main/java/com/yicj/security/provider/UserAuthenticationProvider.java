package com.yicj.security.provider;

import com.yicj.security.dto.JwtUserLoginDTO;
import com.yicj.security.dto.UserInfoDTO;
import com.yicj.security.exception.LoginAuthenticationException;
import com.yicj.security.token.JwtAuthenticationToken;
import com.yicj.security.token.UserAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Objects;

public class UserAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // todo 这里主要进行一个数据库层面的认证，比如账号密码的正确性，比如该账号是否被拉黑有什么权限等，都认证成功之后会组装一个认证通过的token

        // 这里认证成功返回之后会跑到成功处理器：UserLoginSuccessHandler
        // 只要整个认证（包括前面的校验）中有一个地方抛出异常都会调用失败处理器：HttpStatusLoginFailureHandler

        //===============================示例=======================================
        UserAuthenticationToken token = (UserAuthenticationToken) authentication ;

        // 校验正好密码是否正确，同时返回用户信息
        UserInfoDTO userInfo = this.checkAndGetUserInfo(token.getMobile(), token.getPassword());

        // 查询用户角色，假设这里是从数据库中查询出的该用户角色
        //String roleName = "ROLE_BUYER" ;
        String roleName = "ROLE_ADMIN" ;
        //组装并返回认证成功的Token
        JwtUserLoginDTO jwtUserLoginDTO = new JwtUserLoginDTO(
                userInfo.getUserId(), userInfo.getNickname(), userInfo.getMobile(), roleName) ;
        return new JwtAuthenticationToken(jwtUserLoginDTO, null, null);
    }

    private UserInfoDTO checkAndGetUserInfo(String mobile, String password){
        // 根据手机号查询用户信息，这里假设是根据手机号从数据库中查询用户信息
        UserInfoDTO userInfo = null ;
        if ("15600000000".equals(mobile)){
            userInfo = new UserInfoDTO(100000L, "张三", "15600000000", "888888") ;
        }
        if (Objects.isNull(userInfo)){
            throw LoginAuthenticationException.USER_NAME_NOT_EXIST ;
        }
        // 校验密码是否正确
        if (!Objects.equals(userInfo.getPassword(), password)){
            // 密码不正确直接抛出异常
            throw LoginAuthenticationException.PASSWORD_NOT_EXIST ;
        }
        return userInfo ;
    }


    /**
     * 表示这个Provider支持认证的Token （这里是UserAuthenticationToken）
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UserAuthenticationToken.class);
    }
}
