package com.jqdi.easylogin.springbootdemo.config;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.constants.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.repository.OauthRepository;

public class QQClient implements LoginClient {
	private OauthRepository oauthRepository;

	public QQClient(OauthRepository oauthRepository) {
		this.oauthRepository = oauthRepository;
	}

	@Override
	public String login(String ignore1, String ignore2, String qqcode) {
		if (StringUtils.isBlank(qqcode)) {
			throw new LoginException("缺失参数");
		}

		String mobile = "13988888888";
		
		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		if (userId == null) {// 账号不存在
			// 创建新用户
			userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile, null, null);
		}

		// 绑定用户与openid关系
		String openid = "111111111111111111";
		oauthRepository.bindOauth(userId, "qq-openid", openid, qqcode);

		return userId;
	}
}
