package com.yicj.client.client;

import com.yicj.client.common.configuration.UserFeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * UserClient
 * </p>
 *
 * @author yicj
 * @since 2024年07月07日 20:32
 */
@FeignClient(
    name = "user-service",
    contextId = "userClient",
    configuration = UserFeignClientConfiguration.class
)
public interface UserClient {

    @GetMapping("/hello/index")
    String helloInfo(@RequestParam("name") String name) ;

}
