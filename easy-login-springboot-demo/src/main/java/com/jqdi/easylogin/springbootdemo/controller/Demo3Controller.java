package com.jqdi.easylogin.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.spring.boot.starter.LoginType;
import com.jqdi.easylogin.springbootdemo.req.LoginByMobileReq;
import com.jqdi.easylogin.springbootdemo.req.LoginByWeixinAppReq;
import com.jqdi.easylogin.springbootdemo.resp.LoginResp;

@RestController
@RequestMapping("/demo3")
public class Demo3Controller {

	@Autowired
	@Qualifier(LoginType.WEIXIN_APP)
	private LoginClient weixinAppClient;

	@Autowired
	@Qualifier(LoginType.MOBILE_CODE_BIND)
	private LoginClient mobileCodeBindClient;
	
	/**
	 * 微信APP授权（首次会needBind=true，然后走loginByMobileBind逻辑绑定手机号）
	 */
	@PostMapping(value = "/loginByWeixinApp")
	public LoginResp loginByWeixinApp(@RequestBody LoginByWeixinAppReq loginByWeixinAppReq) {
		String wxcode = loginByWeixinAppReq.getWxcode();

		String userId = null;
		try {
			userId = weixinAppClient.login(null, null, wxcode);
		} catch (LoginException e) {
			// "登录失败";
			return new LoginResp();
		}
		
		LoginResp loginResp = new LoginResp();
		if (userId == null) {
			// 用户ID为null代表未登录成功，一般是指通过authcode没有找到对应的用户，需要通过mobileCodeBind来注册用户账号
			loginResp.setNeedBind(true);
			loginResp.setBindCode(wxcode);
			// ->> 前端跳转值绑定手机号/邮箱页面，继续执行逻辑loginByMobileBind
			return loginResp;
		}
		
		loginResp.setNeedBind(false);
		String token = "generate token with userId:" + userId;
		loginResp.setToken(token);
		return loginResp;
	}

	/**
	 * 手机号登录并绑定微信APP的信息
	 */
	@GetMapping(value = "/loginByMobileBind")
	public String loginByMobileBind(@RequestBody LoginByMobileReq loginByMobileReq) {
		String mobile = loginByMobileReq.getMobile();
		String code = loginByMobileReq.getCode();
		String bindCode = loginByMobileReq.getBindCode();
		String userId = null;
		try {
			userId = mobileCodeBindClient.login(mobile, code, bindCode);// 一定会返回userId
		} catch (LoginException e) {
			// "登录失败";
			return "";
		}
		
		String token = "generate token with userId:" + userId;
		return token;
	}
}
