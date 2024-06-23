package com.jqdi.easylogin.core.model;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BindAuthCode {
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 头像
	 */
	private String headimgurl;

	/**
	 * 授权绑定
	 */
	private List<BindUserOauth> binds;

	@Data
	@Accessors(chain = true)
	public static class BindUserOauth {
		/**
		 * 认证类型,{@link com.jqdi.easylogin.core.constants.IdentityType}
		 */
		private String identityType;
		/**
		 * 手机号、邮箱、用户名或第三方应用的唯一标识
		 */
		private String identifier;
	}
}
