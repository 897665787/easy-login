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

	@Bean(LoginType.WEIXIN_MINIAPP)
	LoginClient weixinMiniappService(OauthRepository oauthRepository) {
		String appid = "aaa";
		String secret = "aaa";
		String token = "";
		String aesKey = "";
		String msgDataFormat = "";

		IMaRequest maRequest = new BinarywangMaRequest(appid, secret, token, aesKey, msgDataFormat);
		return new WeixinMiniappClient(oauthRepository, maRequest);
	}
}
