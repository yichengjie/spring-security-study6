1. git-bash生成私钥
    ```text
    keytool -genkeypair --alias ssia -keyalg RSA -keypass ssia123 -keystore ssia.jks -storepass ssia123
    ```
2. 获取公钥
    ```text 
    keytool -list -rfc --keystore ssia.jks | openssl x509 -inform pem -pubkey
    密码： ssia123
    ```
3. 获取token_key
   ```text
   curl -u client:secret http://localhost:8080/oauth/token_key
   ```
   