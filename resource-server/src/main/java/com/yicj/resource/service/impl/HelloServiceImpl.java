package com.yicj.resource.service.impl;

import com.yicj.resource.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * HelloServiceImpl
 * </p>
 *
 * @author yicj
 * @since 2024/08/31 11:48
 */
@Slf4j
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        log.info("[HelloService#hello] hello, {}", name);
        return "hello, " + name;
    }
}
