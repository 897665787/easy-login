package com.jqdi.easylogin.core.wx.miniapp.model;

import lombok.Data;

@Data
public class MaSession {
	private String sessionKey;

	private String openid;

	private String unionid;
}
