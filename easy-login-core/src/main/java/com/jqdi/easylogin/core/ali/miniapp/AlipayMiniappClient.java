package com.jqdi.easylogin.core.ali.miniapp;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.ali.miniapp.request.IAlipayMaRequest;
import com.jqdi.easylogin.core.constants.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.repository.OauthRepository;

/**
 * 支付宝小程序授权登录方式一（本质是拿到手机号登录）
 * 
 * @author JQ棣
 */
public class AlipayMiniappClient implements LoginClient {
	private OauthRepository oauthRepository;
	private IAlipayMaRequest alipayMaRequest;

	public AlipayMiniappClient(OauthRepository oauthRepository, IAlipayMaRequest alipayMaRequest) {
		this.oauthRepository = oauthRepository;
		this.alipayMaRequest = alipayMaRequest;
	}

	@Override
	public String login(String encryptedData, String ignore2, String authcode) {
		if (StringUtils.isBlank(encryptedData)) {
			throw new LoginException("缺失参数");
		}

		String mobile = alipayMaRequest.getMobile(encryptedData);

		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		if (userId == null) {// 账号不存在
			// 创建新用户
			userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile);
		}

		if (StringUtils.isNotBlank(authcode)) {
			String aliUserId = alipayMaRequest.getUserId(authcode);
			// 绑定用户与支付宝userId关系
			oauthRepository.bindOauth(userId, IdentityType.ALI_USERID_MINIAPP, aliUserId, authcode);
		}

		return userId;
	}
}
