package com.jqdi.easylogin.core.model;

import java.util.List;

import com.jqdi.easylogin.core.enums.IdentityType;

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
		private IdentityType identityType;
		private String identifier;
	}
}