package com.jqdi.easylogin.core.ali.miniapp.request;

/**
 * 支付宝小程序API对接
 * 
 * @author JQ棣
 */
public interface IAlipayMaRequest {

	/**
	 * 获取手机号
	 * 
	 * @param encryptedData
	 *            加密数据
	 * @return 手机号
	 */
	String getMobile(String encryptedData);

	/**
	 * 获取用户ID
	 * 
	 * @param authcode
	 *            授权码
	 * @return 用户ID
	 */
	String getUserId(String authcode);

}