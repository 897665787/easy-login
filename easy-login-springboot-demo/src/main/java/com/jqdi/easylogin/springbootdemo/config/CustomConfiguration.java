package com.jqdi.easylogin.springbootdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.wx.miniapp.WeixinMiniappClient;
import com.jqdi.easylogin.core.wx.miniapp.request.BinarywangMaRequest;
import com.jqdi.easylogin.core.wx.miniapp.request.IMaRequest;
import com.jqdi.easylogin.spring.boot.starter.LoginType;

@Configuration
public class CustomConfiguration {

	/**
	 * 定义相同bean名称的登录方式，EasyLoginAutoConfiguration中相同名字的bean就不会初始化
	 * 
	 * @param oauthRepository
	 * @return
	 */
	@Bean(LoginType.WEIXIN_MINIAPP)
	LoginClient weixinMiniappService(OauthRepository oauthRepository) {
		String appid = "aaa";
		String secret = "aaa";

		IMaRequest maRequest = new BinarywangMaRequest(appid, secret);
		return new WeixinMiniappClient(oauthRepository, maRequest);
	}
}
