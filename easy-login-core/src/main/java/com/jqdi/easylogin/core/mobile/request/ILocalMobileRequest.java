package com.jqdi.easylogin.core.mobile.request;

public interface ILocalMobileRequest {
	/**
	 * 获取手机号码
	 * 
	 * @param accessToken
	 *            App 端 SDK 获取的登录 Token
	 * @return
	 */
	String getMobile(String accessToken);
}