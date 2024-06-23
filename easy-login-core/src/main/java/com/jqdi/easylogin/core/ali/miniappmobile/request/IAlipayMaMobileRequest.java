package com.jqdi.easylogin.core.ali.miniappmobile.request;

import com.jqdi.easylogin.core.ali.miniappmobile.model.AliMobileUserId;

/**
 * 支付宝小程序API对接
 * 
 * @author JQ棣
 */
public interface IAlipayMaMobileRequest {

	/**
	 * 获取手机号+用户ID
	 * 
	 * @param authcode
	 *            授权码
	 * @return 手机号+用户ID
	 */
	AliMobileUserId getPhoneNumber(String authcode);

}
