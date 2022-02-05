package com.yicj.hello.service.impl;

import com.yicj.hello.jpa.entity.UserDO;
import com.yicj.hello.mvc.dto.UserDTO;
import com.yicj.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> roles = AuthorityUtils.createAuthorityList("USER_ROLE");
        User user = new User(username, passwordEncoder.encode("123456"), roles) ;
        return user;
    }

    @Override
    public UserDO saveUser(UserDTO userDTO) {
        return null;
    }
}
