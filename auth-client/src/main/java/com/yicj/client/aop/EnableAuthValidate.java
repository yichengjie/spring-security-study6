package com.yicj.client.aop;

import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AuthValidateRegistrar.class)
public @interface EnableAuthValidate {

    String basePackage() default "" ;

    int order() default Ordered.LOWEST_PRECEDENCE;
}
