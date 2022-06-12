package com.yicj.security.token;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@ToString
public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private String mobile;
    private String password;

    public UserAuthenticationToken(String mobile, String password){
        super(null);
        this.mobile = mobile ;
        this.password = password ;
        this.setAuthenticated(false);
    }

    public UserAuthenticationToken(String mobile,String password, Collection<? extends GrantedAuthority> authorities){
        super(authorities) ;
        this.mobile = mobile ;
        this.password = password ;
        super.setAuthenticated(true);
    }


    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }


    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public Object getPrincipal() {
        return this.mobile;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        password = null;
    }
}
