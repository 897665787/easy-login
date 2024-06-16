package com.jqdi.easylogin.core.repository;

import com.jqdi.easylogin.core.model.BindAuthCode;

/**
 * 授权数据临时存储
 * 
 * <pre>
 * 使用场景：微信APP、微信公众号登录过程中需要绑定手机号
 * </pre>
 * 
 * @author JQ棣
 */
public interface OauthTempRepository {
	/**
	 * 保存绑定授权码
	 * 
	 * @param bindCode
	 *            绑定码
	 * @param bindAuthCode
	 *            绑定信息
	 */
	void saveBindAuthCode(String bindCode, BindAuthCode bindAuthCode);

	/**
	 * 查询绑定授权码
	 * 
	 * @param bindCode
	 *            绑定码
	 * @return
	 */
	BindAuthCode getBindAuthCode(String bindCode);
}
