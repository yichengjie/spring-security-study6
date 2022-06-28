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
   redirect_uri: http://localhost:8080/oauth/callback
   ```
5. 

