package com.jqdi.easylogin.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 认证类型
 */
@AllArgsConstructor
public enum IdentityType {

	EMAIL("email", "邮箱"), // 国外一般会以邮箱作为基准
	MOBILE("mobile", "手机号"), // 国内一般会以手机号作为基准

	USERNAME("username", "用户名"), //

	// 授权登录方式
	WX_UNIONID("wx-unionid", "微信unionid"), //
	WX_OPENID_APP("wx-openid-app", "微信APP openid"), //
	WX_OPENID_MINIAPP("wx-openid-miniapp", "微信小程序openid"), //
	WX_OPENID_MP("wx-openid-mp", "微信公众号openid"), //

	ALI_USERID_MINIAPP("ali-userid-miniapp", "支付宝小程序userId"), //

	// SINA("sina", "新浪微博"), //
	// QQ("qq", "QQ"), //
	;

	@Getter
	private String code;

	@Getter
	private String desc;

	public static IdentityType of(String code) {
		for (IdentityType item : IdentityType.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
