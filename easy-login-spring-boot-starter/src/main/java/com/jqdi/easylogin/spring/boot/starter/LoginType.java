package com.jqdi.easylogin.spring.boot.starter;

public interface LoginType {
	String USERNAME_PASSWORD = "usernamePassword";// 用户名+密码
	String MOBILE_PASSWORD = "mobilePassword";// 手机号+密码
	String EMAIL_PASSWORD = "emailPassword";// 邮箱+密码

	String LOCAL_MOBILE = "localMobile";// 本地手机号
	String MOBILE_CODE = "mobileCode";// 手机号+验证码
	String MOBILE_CODE_BIND = "mobileCodeBind";// 手机号+验证码+绑定前置步骤信息

	String EMAIL_CODE = "emailCode";// 邮箱+验证码
	String EMAIL_CODE_BIND = "emailCodeBind";// 邮箱+验证码+绑定前置步骤信息

	String WEIXIN_APP = "weixinApp";// 微信app授权登录
	String WEIXIN_MINIAPP = "weixinMiniapp";// 微信小程序授权登录，方式一
	String WEIXIN_MINIAPP_MOBILE = "weixinMiniappMobile";// 微信小程序授权登录，方式二
	String WEIXIN_MP = "weixinMp";// 微信公众号授权登录

	String ALIPAY_MINIAPP = "alipayMiniapp";// 支付宝小程序授权登录，方式一
	String ALIPAY_MINIAPP_MOBILE = "alipayMiniappMobile";// 支付宝小程序授权登录，方式二
}
