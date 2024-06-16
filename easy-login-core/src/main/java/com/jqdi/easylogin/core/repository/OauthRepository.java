package com.jqdi.easylogin.core.repository;

import com.jqdi.easylogin.core.enums.IdentityType;

/**
 * 授权数据存储
 * 
 * @author JQ棣
 */
public interface OauthRepository {
	/**
	 * 获取用户ID
	 * 
	 * @param identityType
	 *            认证类型
	 * @param identifier
	 *            手机号、邮箱、用户名或第三方应用的唯一标识
	 * @return 用户ID
	 */
	String getUserId(IdentityType identityType, String identifier);

	/**
	 * 保存授权信息
	 * 
	 * @param userId
	 *            用户ID
	 * @param identityType
	 *            认证类型
	 * @param identifier
	 *            手机号、邮箱、用户名或第三方应用的唯一标识
	 * @param certificate
	 *            凭证(站内的保存密码，站外的不保存或保存token)
	 */
	void bindOauth(String userId, IdentityType identityType, String identifier, String certificate);

	/**
	 * 注册用户
	 * 
	 * @param identityType
	 *            认证类型
	 * @param identifier
	 *            手机号、邮箱、用户名或第三方应用的唯一标识
	 * @param nickname
	 *            昵称
	 * @param avatar
	 *            头像
	 * @return 用户ID
	 */
	String registerUser(IdentityType identityType, String identifier, String nickname, String avatar);
}
