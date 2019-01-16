#### 一、密码模式
##### （1）认证服务器（此认证服务器结合了资源服务器，即认证服务和资源服务一体。但在认证和资源分离的情况下，也充当认证服务器）
> ##### 获取token (post): 
>> http://localhost:8001/oauth/token?username=liuzj&password=123&grant_type=password&client_id=client&client_secret=123456
> ##### 检查token (get): 
>> http://localhost:8001/oauth/check_token?token=f0cb83c3-6dd7-4c63-ab9f-2bdf3d492b46
> ##### 刷新token (post):
>> http://localhost:8001/oauth/token?grant_type=refresh_token&refresh_token=cd08de58-b91f-45c6-b165-6b8bd4b7bfdc&client_id=client&client_secret=123456

##### （2）资源服务器（ client 服务器通过账号和密码从认证服务器获取对应的token，然后携带 token 去访问资源服务器的资源）
> 资源服务器可以获得用户的账号和角色，在token认证通过的情况下，还可以自定义角色等信息过滤

#### 二、授权码模式
##### （1）具体操作流程
- 用户访问客户端，后者将前者导向认证服务器，认证服务器返回认证页面（账号密码或者其他认证方式）

- 用户选择是否给予客户端授权。

- 假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。

- 客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌。这一步是在客户端的后台的服务器上完成的，对用户不可见。

- 认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。
##### （2）请求过程
> ##### 客户端请求认证服务器 (get): 
>> http://localhost:8001/oauth/authorize?client_id=client&response_type=code&scope=all&redirect_uri=http://www.baidu.com
> ##### 认证服务器返回:
>> https://www.baidu.com/?code=AKAQUe
> ##### 客户端拿到 code 去换取 access_token (post):
>> http://localhost:8001/oauth/token?client_id=client&client_secret=123456&grant_type=authorization_code&redirect_uri=http://www.baidu.com&code=AKAQUe
> ##### 客户端拿到 access_token 去访问资源: