package com.yicj.client.common.configuration;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * <p>
 * AuthorizedUserFeignClient
 * </p>
 *
 * @author yicj
 * @since 2024年07月07日 20:11
 */
@FeignClient
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface UserFeignClient {

    @AliasFor(
        attribute = "name",
        annotation = FeignClient.class
    )
    String value() default "";

    /**
     * This will be used as the bean name instead of name if present, but will not be used
     * as a service id.
     * @return bean name instead of name if present
     */
    @AliasFor(
        attribute = "contextId",
        annotation = FeignClient.class
    )
    String contextId() default "";

    /**
     * @return The service id with optional protocol prefix. Synonym for {@link #value()
     * value}.
     */
    @AliasFor(
        attribute = "value",
        annotation = FeignClient.class
    )
    String name() default "";

    /**
     * @return the <code>@Qualifier</code> value for the feign client.
     * @deprecated in favour of {@link #qualifiers()}.
     *
     * If both {@link #qualifier()} and {@link #qualifiers()} are present, we will use the
     * latter, unless the array returned by {@link #qualifiers()} is empty or only
     * contains <code>null</code> or whitespace values, in which case we'll fall back
     * first to {@link #qualifier()} and, if that's also not present, to the default =
     * <code>contextId + "FeignClient"</code>.
     */
    @AliasFor(
        attribute = "qualifier",
        annotation = FeignClient.class
    )
    @Deprecated
    String qualifier() default "";

    /**
     * @return the <code>@Qualifiers</code> value for the feign client.
     *
     * If both {@link #qualifier()} and {@link #qualifiers()} are present, we will use the
     * latter, unless the array returned by {@link #qualifiers()} is empty or only
     * contains <code>null</code> or whitespace values, in which case we'll fall back
     * first to {@link #qualifier()} and, if that's also not present, to the default =
     * <code>contextId + "FeignClient"</code>.
     */
    @AliasFor(
        attribute = "qualifiers",
        annotation = FeignClient.class
    )
    String[] qualifiers() default {};

    /**
     * @return an absolute URL or resolvable hostname (the protocol is optional).
     */
    @AliasFor(
        attribute = "url",
        annotation = FeignClient.class
    )
    String url() default "";

    /**
     * @return whether 404s should be decoded instead of throwing FeignExceptions
     */
    @AliasFor(
        attribute = "decode404",
        annotation = FeignClient.class
    )
    boolean decode404() default false;

    /**
     * A custom configuration class for the feign client. Can contain override
     * <code>@Bean</code> definition for the pieces that make up the client, for instance
     * {@link feign.codec.Decoder}, {@link feign.codec.Encoder}, {@link feign.Contract}.
     *
     * @see FeignClientsConfiguration for the defaults
     * @return list of configurations for feign client
     */
    @AliasFor(
        attribute = "configuration",
        annotation = FeignClient.class
    )
    Class<?>[] configuration() default {UserFeignClientConfiguration.class};

    /**
     * Fallback class for the specified Feign client interface. The fallback class must
     * implement the interface annotated by this annotation and be a valid spring bean.
     * @return fallback class for the specified Feign client interface
     */
    @AliasFor(
        attribute = "fallback",
        annotation = FeignClient.class
    )
    Class<?> fallback() default void.class;

    /**
     * Define a fallback factory for the specified Feign client interface. The fallback
     * factory must produce instances of fallback classes that implement the interface
     * annotated by {@link FeignClient}. The fallback factory must be a valid spring bean.
     *
     * @see feign.hystrix.FallbackFactory for details.
     * @see FallbackFactory for details.
     * @return fallback factory for the specified Feign client interface
     */
    @AliasFor(
        attribute = "fallbackFactory",
        annotation = FeignClient.class
    )
    Class<?> fallbackFactory() default void.class;

    /**
     * @return path prefix to be used by all method-level mappings. Can be used with or
     * without <code>@RibbonClient</code>.
     */
    @AliasFor(
        attribute = "path",
        annotation = FeignClient.class
    )
    String path() default "";

    /**
     * @return whether to mark the feign proxy as a primary bean. Defaults to true.
     */
    @AliasFor(
        attribute = "primary",
        annotation = FeignClient.class
    )
    boolean primary() default true;

}
