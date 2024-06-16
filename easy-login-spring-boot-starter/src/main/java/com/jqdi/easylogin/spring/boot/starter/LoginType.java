package com.jqdi.easylogin.spring.boot.starter;

public interface LoginType {
	String USERNAME_PASSWORD = "usernamePassword";
	String MOBILE_PASSWORD = "mobilePassword";
	String EMAIL_PASSWORD = "emailPassword";
	
	String LOCAL_MOBILE = "localMobile";
	String MOBILE_CODE = "mobileCode";
	String MOBILE_CODE_BIND = "mobileCodeBind";
	
	String EMAIL_CODE = "emailCode";
	String EMAIL_CODE_BIND = "emailCodeBind";
	
	String WEIXIN_APP = "weixinApp";
	String WEIXIN_MINIAPP = "weixinMiniapp";
	String WEIXIN_MINIAPP_MOBILE = "weixinMiniappMobile";
	String WEIXIN_MP = "weixinMp";

	String ALIPAY_MINIAPP = "alipayMiniapp";
	String ALIPAY_MINIAPP_MOBILE = "alipayMiniappMobile";

}
