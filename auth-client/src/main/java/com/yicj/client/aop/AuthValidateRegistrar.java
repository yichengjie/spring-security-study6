package com.yicj.client.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

@Slf4j
public class AuthValidateRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        MultiValueMap<String, Object> attributes =
                importingClassMetadata.getAllAnnotationAttributes(EnableAuthValidate.class.getName());
        String basePackage = (String)attributes.getFirst("basePackage");
        Integer order = (Integer)attributes.getFirst("order");
        BeanDefinitionBuilder builder =
                BeanDefinitionBuilder.rootBeanDefinition(AuthValidateFactoryBean.class) ;
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE) ;
        builder.addConstructorArgValue(basePackage) ;
        builder.addConstructorArgValue(order) ;
        registry.registerBeanDefinition("authValidateRegistrar", builder.getBeanDefinition());
    }
}
