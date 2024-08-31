#### 授权页面地址
1. 浏览器访问：http://localhost:8080/oauth/authorize?response_type=code&client_id=client&scope=read
#### 获取token
1. 访问地址：http://localhost:8080/oauth/token
2. 使用Post Man 发送 POST 请求
3. Authorization选择： Basic Auth，内容填写client_id 和client_secret
    ```text
    Username: client_id
    Password: client_secret
    ```
4. Body中添加x-www-form-urlencoded
   ```text
   grant_type: authorization_code
   code: yRih2e
   redirect_uri: https://www.baidu.com
   ```
5. 授权服务器端点：TokenEndpoint、AuthorizationEndpoint
6. SpringSecurity异常转换器：ExceptionTranslationFilter
   ```text
   https://blog.csdn.net/yang131peng/article/details/118726288
   ```
7. 用户授权页面：forward:/oauth/confirm_access--> WhitelabelApprovalEndpoint
8. 授权服务器配置类：AuthorizationServerEndpointsConfiguration
9. 用户授权后提交表单到：method为post的/oauth/authorize端点
10. 自定义页面：https://www.163.com/dy/article/HB1B7S7905313LFD.html
11. JwtTokenStore -> JwtAccessTokenConverter 
12. FilterChainProxy#doFilterInternal -> getFilters 从filterChains中找到匹配的filterChain
### 密码模式
1. 访问地址
   ```
   POST /oauth/token?username=john&password=12345&grant_type=password&client_id=client
   Authentication: Basic base64(client_id:client_secret)
   ```
2. 