package com.jqdi.easylogin.core.mobile;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.constants.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.repository.VerifycodeRepository;
import com.jqdi.easylogin.core.repository.OauthRepository;

public class MobileCodeClient implements LoginClient {
	private OauthRepository oauthRepository;
	private VerifycodeRepository verifycodeRepository;

	public MobileCodeClient(OauthRepository oauthRepository, VerifycodeRepository verifycodeRepository) {
		this.oauthRepository = oauthRepository;
		this.verifycodeRepository = verifycodeRepository;
	}
	
	@Override
	public String login(String mobile, String code, String ignore3) {
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(code)) {
			throw new LoginException("缺失参数");
		}
		// 核对验证码
		boolean checkVerifyCode = verifycodeRepository.checkVerifycode(mobile, code);
		if (!checkVerifyCode) {
			throw new LoginException("验证码错误");
		}

		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		if (userId == null) {// 账号不存在
			// 创建新用户
			userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile, null, null);
		}
		
		return userId;
	}
}
