1. 编写FeignClient 配置类
    ```java
    public class UserFeignClientConfiguration {
        @Bean("userFeignClientConfiguration")
        public RequestInterceptor getOAuth2RequestInterceptor() {
            return (RequestTemplate template) -> {
                //todo  获取当前用户的token 传递给feign调用的服务
                String token = "token";
                template.header("Authorization", "Bearer " + token);
            };
        }
    
        @Bean
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }
    }
    ```
2. 编写FeignClient 接口
    ```java
    @FeignClient(
        name = "user-service",
        contextId = "userClient",
        configuration = UserFeignClientConfiguration.class
    )
    public interface UserClient {
    
        @GetMapping("/hello/index")
        String helloInfo(@RequestParam("name") String name) ;
    
    }
    ```