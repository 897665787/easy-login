package com.jqdi.easylogin.core.mobile;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.constants.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.mobile.request.ILocalMobileRequest;
import com.jqdi.easylogin.core.repository.OauthRepository;

public class LocalMobileClient implements LoginClient {
	private OauthRepository oauthRepository;
	private ILocalMobileRequest localMobileRequest;
	
	public LocalMobileClient(OauthRepository oauthRepository, ILocalMobileRequest localMobileRequest) {
		this.oauthRepository = oauthRepository;
		this.localMobileRequest = localMobileRequest;
	}
	
	@Override
	public String login(String accessToken, String ignore2, String ignore3) {
		if (StringUtils.isBlank(accessToken)) {
			throw new LoginException("缺失参数");
		}
		// 通过3大运营商获取本机手机号码，可直接使用
		String mobile = localMobileRequest.getMobile(accessToken);
		String userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile, null, null);
		return userId;
	}
}
