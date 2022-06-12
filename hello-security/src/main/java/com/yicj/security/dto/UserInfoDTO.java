package com.yicj.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO implements Serializable {
    /**
     * 用户Id
     */
    private Long userId ;

    /**
     * 昵称
     */
    private String nickname ;

    /**
     * 手机号
     */
    private String mobile ;

    /**
     * 密码
     */
    private String password ;
}
