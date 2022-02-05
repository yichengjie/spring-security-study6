package com.yicj.hello.service;

import com.yicj.hello.jpa.entity.UserDO;
import com.yicj.hello.mvc.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDO saveUser(UserDTO userDTO) ;
}
