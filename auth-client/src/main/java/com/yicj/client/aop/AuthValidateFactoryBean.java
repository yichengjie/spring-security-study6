package com.yicj.client.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.FactoryBean;


@Slf4j
public class AuthValidateFactoryBean implements FactoryBean<AspectJExpressionPointcutAdvisor> {

    private String basePackage ;
    private Integer order ;

    public AuthValidateFactoryBean(String basePackage, Integer order){
        this.basePackage = basePackage ;
        this.order = order ;
    }

    @Override
    public AspectJExpressionPointcutAdvisor getObject() throws Exception {

        MethodInterceptor interceptor = invocation -> {
            log.info("===> before invocation method");
            Object proceed = invocation.proceed();
            log.info("===> after invocation method");
            return proceed ;
        } ;

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor() ;
        advisor.setExpression("execution (public * "+basePackage+"*..*(..))");
        advisor.setAdvice(interceptor);
        advisor.setOrder(order);

        return advisor;
    }

    @Override
    public Class<?> getObjectType() {
        return AspectJExpressionPointcutAdvisor.class;
    }
}
