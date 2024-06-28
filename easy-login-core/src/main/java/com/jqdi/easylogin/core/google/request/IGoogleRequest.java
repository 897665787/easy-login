package com.jqdi.easylogin.core.google.request;

/**
 * QQ请求
 * 
 * @author JQ棣
 */
public interface IGoogleRequest {

	/**
	 * 获取openid
	 * 
	 * @param code        授权码
	 * @param redirectUri 与（Step1：获取Authorization Code）中传入的redirect_uri保持一致
	 * @return
	 */
	String getOpenid(String code, String redirectUri);

}
