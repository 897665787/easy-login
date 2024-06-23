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
@RequestMapping("/demo1")
public class Demo1Controller {

	@Autowired
	@Qualifier(LoginType.MOBILE_CODE)
	private LoginClient mobileCodeClient;

	/**
	 * 手机号+验证码
	 * 
	 * @param mobile
	 *            手机号
	 * @param code
	 *            验证码
	 * @return
	 */
	@GetMapping(value = "/loginByMobile")
	public String loginByMobile(String mobile, String code) {
		String userId = null;
		try {
			userId = mobileCodeClient.login(mobile, code, null);
		} catch (LoginException e) {
			// "登录失败";
			return "";
		}
		
		String token = "generate token with userId:" + userId;
		return token;
	}
}
