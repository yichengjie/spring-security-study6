package com.yicj.authserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: spring-security-study6
 * @description:
 * @author: yicj1
 * @create: 2022-06-28 13:46
 **/
@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @ResponseBody
    @GetMapping("/callback")
    public String authCallback(@RequestParam("code") String code){
        return "ret callback code : " + code ;
    }
}
