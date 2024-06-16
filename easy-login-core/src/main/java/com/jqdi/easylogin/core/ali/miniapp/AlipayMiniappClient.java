package com.jqdi.easylogin.core.ali.miniapp;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.ali.miniapp.request.IAliMaRequest;
import com.jqdi.easylogin.core.enums.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.repository.OauthRepository;

public class AlipayMiniappClient implements LoginClient {
	private OauthRepository oauthRepository;
	private IAliMaRequest aliMaRequest;

	public AlipayMiniappClient(OauthRepository oauthRepository, IAliMaRequest aliMaRequest) {
		this.oauthRepository = oauthRepository;
		this.aliMaRequest = aliMaRequest;
	}

	@Override
	public String login(String encryptedData, String ignore2, String authcode) {
		if (StringUtils.isBlank(encryptedData)) {
			throw new LoginException("缺失参数");
		}

		String mobile = aliMaRequest.getMobile(encryptedData);

		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		if (userId == null) {// 账号不存在
			// 创建新用户
			userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile, null, null);
		}

		if (StringUtils.isNotBlank(authcode)) {
			String aliUserId = aliMaRequest.getUserId(authcode);
			// 绑定用户与支付宝userId关系
			oauthRepository.bindOauth(userId, IdentityType.ALI_USERID_MINIAPP, aliUserId, authcode);
		}

		return userId;
	}
}
