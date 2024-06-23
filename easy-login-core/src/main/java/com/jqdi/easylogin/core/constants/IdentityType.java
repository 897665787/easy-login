package com.jqdi.easylogin.core.constants;

/**
 * 认证类型
 */
public interface IdentityType {

	String EMAIL = "email";// 邮箱 国外一般会以邮箱作为基准
	String MOBILE = "mobile";// 手机号 国内一般会以手机号作为基准

	String USERNAME = "username";// 用户名

	// 微信授权登录
	String WX_UNIONID = "wx-unionid";// 微信unionid
	String WX_OPENID_APP = "wx-openid-app";// 微信APP openid
	String WX_OPENID_MINIAPP = "wx-openid-miniapp";// 微信小程序openid
	String WX_OPENID_MP = "wx-openid-mp";// 微信公众号openid

	// 支付宝授权登录
	String ALI_USERID_MINIAPP = "ali-userid-miniapp";// 支付宝小程序userId

	// String SINA = "sina";// 新浪微博
	// String QQ = "qq";// QQ
}