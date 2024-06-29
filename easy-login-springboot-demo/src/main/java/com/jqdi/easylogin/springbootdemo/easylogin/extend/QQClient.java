package com.jqdi.easylogin.springbootdemo.easylogin.extend;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.repository.OauthRepository;

@Component
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
		
		String userId = oauthRepository.getUserId("qq", mobile);
		if (userId == null) {// 账号不存在
			// 创建新用户
			userId = oauthRepository.registerUser("qq", mobile);
		}

		// 绑定用户与openid关系
		String openid = "111111111111111111";
		oauthRepository.bindOauth(userId, "qq-openid", openid, qqcode);

		return userId;
	}
}
