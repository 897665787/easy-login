package com.jqdi.easylogin.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.spring.boot.starter.LoginType;

@RestController
@RequestMapping("/demo2")
public class Demo2Controller {
	
	@Autowired
	@Qualifier(LoginType.WEIXIN_MINIAPP_MOBILE)
	private LoginClient weixinMiniappMobileClient;

	/**
	 * 微信小程序
	 * 
	 * @param wxcode
	 *            微信授权码
	 * @return
	 */
	@GetMapping(value = "/loginByWeixinMiniappMobile")
	public String loginByWeixinMiniappMobile(String wxcode) {
		String userId = null;
		try {
			userId = weixinMiniappMobileClient.login(null, null, wxcode);
		} catch (LoginException e) {
			// "登录失败";
			return "";
		}
		
		String token = "generate token with userId:" + userId;
		return token;
	}
}
