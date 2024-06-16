package com.jqdi.easylogin.core.wx.mp.model;

import lombok.Data;

@Data
public class MpAccessToken {
	private String accessToken;

	private String openid;

	/**
	 * <pre>
	 * https://mp.weixin.qq.com/cgi-bin/announce?action=getannouncement&announce_id=11513156443eZYea&version=&lang=zh_CN.
	 * 本接口在scope参数为snsapi_base时不再提供unionID字段。
	 * </pre>
	 */
	private String unionid;
}
