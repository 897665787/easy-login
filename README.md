一个封装了登录的框架，支持密码、本机号码一键登录、微信授权登录（小程序、APP、公众号）、支付宝授权登录（小程序）等

### 登录方式支持

| 登录方式                   | 已实现 |
| -------------------------- |--------|
| 用户名+密码                | √      |
| 手机号+密码            	 | √      |
| 邮箱+密码                  | √      |
| 本机号码一键登录           | √      |
| 手机号+验证码              | √      |
| 邮箱+验证码                | √      |
| 微信授权登录（APP）        | √      |
| 微信授权登录（小程序）     | √      |
| 微信授权登录（公众号）     | √      |
| 支付宝授权登录（小程序）   | √      |
| QQ授权登录                 | ✘      |
| 新浪微博授权登录           | ✘      |
| 国外（如google）授权登录   | ✘      |

### 模块说明

```lua
easy-login
├── easy-login-core -- 核心代码
	 └── ali -- 支付宝授权登录
	 └── email -- 邮箱登录
	 └── mobile -- 手机号登录
	 └── password -- 密码登录
	 └── wx -- 微信授权登录
	 └── repository -- 持久化信息接口
└── easy-login-spring-boot-starter -- 整合springboot代码
└── easy-login-springboot-demo -- 在springboot中使用easy-login的demo代码
```

### SpringBoot自动装配条件

| 登录方式                   | 实现类                        | 装配条件                                    |
| -------------------------- | ----------------------------- |---------------------------------------------|
| 用户名+密码                | UsernamePasswordClient        | 实现PasswordRepository                      |
| 手机号+密码            	 | MobilePasswordClient          | 实现PasswordRepository                      |
| 邮箱+密码                  | EmailPasswordClient           | 实现PasswordRepository                      |
| 本机号码一键登录           | LocalMobileClient             | 配置easylogin.localMobile.accessKeyId       |
| 手机号+验证码              | MobileCodeClient              | 实现VerifycodeRepository                    |
| 手机号+验证码+绑定授权     | MobileCodeBindClient          | 实现VerifycodeRepository                    |
| 邮箱+验证码                | EmailCodeClient               | 实现VerifycodeRepository                    |
| 邮箱+验证码+绑定授权       | EmailCodeBindClient           | 实现VerifycodeRepository                    |
| 微信授权登录（APP）        | WeixinAppClient               | 配置easylogin.weixinApp.appid               |
| 微信授权登录（小程序）     | WeixinMiniappClient           | 配置easylogin.weixinMiniapp.appid           |
| 微信授权登录（小程序）     | WeixinMiniappMobileClient     | 配置easylogin.weixinMiniappMobile.appid     |
| 微信授权登录（公众号）     | WeixinMpClient                | 配置easylogin.weixinMp.appid                |
| 支付宝授权登录（小程序）   | AlipayMiniappClient           | 配置easylogin.alipayMiniapp.appid           |
| 支付宝授权登录（小程序）   | AlipayMiniappMobileClient     | 配置easylogin.alipayMiniappMobile.appid     |

### 使用说明

#### 1：编译源码
mvn install，使用maven将源码编译成jar包并且安装到本地仓库，如有私服也可以部署到私服

#### 2：jar包引用（如使用微信授权登录（小程序）），其他可参考easy-login-core的pom配置

```
<dependency>
    <groupId>com.jqdi</groupId>
    <artifactId>easy-login-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
<!-- 微信sdk -->
<dependency>
	<groupId>com.github.binarywang</groupId>
	<artifactId>weixin-java-miniapp</artifactId>
	<version>4.3.0</version>
	<scope>provided</scope>
</dependency>
```
#### 3：springboot yml 配置（如使用微信授权登录（小程序）），其他可参考easy-login-springboot-demo的pom配置
```
easylogin:
  weixinMiniappMobile: # 微信小程序登录方式二
    appid: 1111 #微信小程序的appid
    secret: 1111 #微信小程序的Secret
    token: 1111 #微信小程序消息服务器配置的token
    aesKey: 1111 #微信小程序消息服务器配置的EncodingAESKey
    msgDataFormat: JSON
```
#### 4：在代码中使用
```
	@Autowired
	@Qualifier(LoginType.WEIXIN_MINIAPP_MOBILE)
	private LoginClient weixinMiniappMobileClient;

	public String loginByWeixinMiniappMobile(String wxcode) {
		String userId = weixinMiniappMobileClient.login(null, null, wxcode);
		String token = "generate token with userId:" + userId;
		return token;
	}
```

### 开源共建

#### 开源协议

easy-login 开源软件遵循 [Apache 2.0 协议](https://www.apache.org/licenses/LICENSE-2.0.html)。
允许商业使用，但务必保留类作者、Copyright 信息。

#### 其他说明

1. 联系作者 <a href="mailto:897665787@qq.com">897665787@qq.com</a>
