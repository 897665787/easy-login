package com.jqdi.easylogin.core.qq.request;

import com.jqdi.easylogin.core.qq.model.TokenOpenid;
import com.jqdi.easylogin.core.qq.model.UserInfo;

/**
 * QQ请求
 * 
 * @author JQ棣
 */
public interface IQQRequest {
	/**
	 * 获取accessToken和openid
	 * 
	 * @param code
	 *            Step1中的授权码
	 * @param redirectUri
	 *            与（Step1：获取Authorization Code）中传入的redirect_uri保持一致
	 * @return accessToken和openid
	 */
	TokenOpenid token(String code, String redirectUri);

	/**
	 * 获取openid
	 * 
	 * @param accessToken
	 *            accessToken
	 * @return openid
	 */
	String getOpenid(String accessToken);

	/**
	 * 获取用户信息（获取登录用户在QQ空间的信息，包括昵称、头像、性别及黄钻信息（包括黄钻等级、是否年费黄钻等））
	 * 
	 * @param accessToken
	 *            accessToken
	 * @param openid
	 *            openid
	 * @return
	 */
	UserInfo getUserInfo(String accessToken, String openid);

}
