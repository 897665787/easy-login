package com.jqdi.easylogin.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.spring.boot.starter.LoginType;
import com.jqdi.easylogin.springbootdemo.resp.LoginResp;

@RestController
@RequestMapping("/account")
public class AccountController {

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
		String userId = mobileCodeClient.login(mobile, code, null);
		String token = "generate token with userId:" + userId;
		return token;
	}
	
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
		String userId = weixinMiniappMobileClient.login(null, null, wxcode);
		String token = "generate token with userId:" + userId;
		return token;
	}

	@Autowired
	@Qualifier(LoginType.WEIXIN_APP)
	private LoginClient weixinAppClient;

	/**
	 * 微信APP授权（首次会needBind=true，然后走loginByMobileBind逻辑绑定手机号）
	 * 
	 * @param mobile
	 *            手机号
	 * @param authcode
	 *            授权码
	 * @return
	 */
	@GetMapping(value = "/loginByWeixinApp")
	public LoginResp loginByWeixinApp(String mobile, String authcode) {
		LoginResp loginResp = new LoginResp();
		loginResp.setNeedBind(false);

		String userId = weixinAppClient.login(mobile, null, authcode);
		if (userId == null) {
			// 用户ID为null代表未登录成功，一般是指通过authcode没有找到对应的用户，需要通过mobileCodeBind来注册用户账号
			loginResp.setNeedBind(true);
			loginResp.setBindCode(authcode);
			// ->> 前端跳转值绑定手机号/邮箱页面，继续执行逻辑loginByMobileBind
			return loginResp;
		}

		String token = "generate token with userId:" + userId;
		loginResp.setToken(token);
		return loginResp;
	}

	@Autowired
	@Qualifier(LoginType.MOBILE_CODE_BIND)
	private LoginClient mobileCodeBindClient;

	/**
	 * 手机号登录并绑定微信APP的信息
	 * 
	 * @param mobile
	 *            手机号
	 * @param code
	 *            验证码
	 * @param authcode
	 *            loginByWeixinApp响应的bindCode
	 * @return
	 */
	@GetMapping(value = "/loginByMobileBind")
	public String loginByMobileBind(String mobile, String code, String bindCode) {
		String userId = mobileCodeBindClient.login(mobile, code, bindCode);// 一定会返回userId
		String token = "generate token with userId:" + userId;
		return token;
	}
}
