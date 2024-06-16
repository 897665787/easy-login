package com.jqdi.easylogin.core.wx.mp.model;

import lombok.Data;

@Data
public class MpUserInfo {
	/**
	 * openid 普通用户的标识，对当前开发者帐号唯一
	 */
	private String openid;
	/**
	 * nickname 普通用户昵称
	 */
	private String nickname;
	/**
	 * headimgurl 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），
	 * 用户没有头像时该项为空
	 */
	private String headimgurl;
	/**
	 * unionid 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
	 */
	private String unionid;
}
