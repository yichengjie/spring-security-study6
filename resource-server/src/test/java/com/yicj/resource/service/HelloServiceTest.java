package com.yicj.resource.service;

import com.yicj.resource.config.TestServiceConfiguration;
import com.yicj.resource.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * <p>
 * HelloServiceTest
 * </p>
 *
 * @author yicj
 * @since 2024/08/31 11:51
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
    classes = {TestServiceConfiguration.class},
    initializers = {ConfigFileApplicationContextInitializer.class}
)
@WebMvcTest
public class HelloServiceTest {

    @Autowired
    private WebApplicationContext wac ;

    private static MockMvc mvc ;

    private static MockHttpSession session ;

    @MockBean
    private HelloService helloService ;

    @BeforeEach
    void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build() ;
        //
        session = new MockHttpSession() ;
        //设置session
        UserInfo userInfo = new UserInfo() ;
        userInfo.setUsername("yicj");
        userInfo.setPassword("123456");
        session.setAttribute("userInfo", userInfo) ;
    }

    @Test
    void test(){
        String retValue = helloService.hello("yicj");
        log.info("hello world, {}", retValue);
        when(helloService.hello("yicj")).thenAnswer(invocation -> {
            String name = invocation.getArgument(0) ;
            return "hello test, " + name ;
        });
        String retValue2 = helloService.hello("yicj");
        log.info("hello world, {}", retValue2);
        verify(helloService, Mockito.times(2)).hello("yicj") ;
//        given(helloService.hello("yicj")).willThrow(new RuntimeException("mock exception")) ;
//        helloService.hello("yicj");

    }
}
