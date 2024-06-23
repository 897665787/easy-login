package com.jqdi.easylogin.core.mobile.request;

/**
 * 本机手机号码API对接
 * 
 * @author JQ棣
 */
public interface ILocalMobileRequest {
	/**
	 * 获取手机号码
	 * 
	 * @param accessToken
	 *            App 端 SDK 获取的登录 Token
	 * @return 手机号码
	 */
	String getMobile(String accessToken);
}