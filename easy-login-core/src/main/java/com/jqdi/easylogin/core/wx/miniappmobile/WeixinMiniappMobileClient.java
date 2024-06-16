package com.jqdi.easylogin.core.wx.miniappmobile;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.enums.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.wx.miniappmobile.request.IMaMobileRequest;

public class WeixinMiniappMobileClient implements LoginClient {
	private OauthRepository oauthRepository;
	private IMaMobileRequest maMobileRequest;

	public WeixinMiniappMobileClient(OauthRepository oauthRepository, IMaMobileRequest maMobileRequest) {
		this.oauthRepository = oauthRepository;
		this.maMobileRequest = maMobileRequest;
	}

	@Override
	public String login(String ignore1, String ignore2, String code) {
		if (StringUtils.isBlank(code)) {
			throw new LoginException("缺失参数");
		}

		String mobile = maMobileRequest.getMobile(code);

		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		if (userId == null) {// 账号不存在
			// 创建新用户
			userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile, null, null);
		}
		return userId;
	}
}
