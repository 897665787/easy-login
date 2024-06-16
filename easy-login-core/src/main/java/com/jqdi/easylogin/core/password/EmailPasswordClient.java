package com.jqdi.easylogin.core.password;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.enums.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.repository.PasswordRepository;
import com.jqdi.easylogin.core.repository.OauthRepository;

public class EmailPasswordClient implements LoginClient {

	private OauthRepository oauthRepository;
	private PasswordRepository passwordRepository;

	public EmailPasswordClient(OauthRepository oauthRepository, PasswordRepository passwordRepository) {
		this.oauthRepository = oauthRepository;
		this.passwordRepository = passwordRepository;
	}

	@Override
	public String login(String email, String password, String ignore3) {
		if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
			throw new LoginException("缺失参数");
		}

		String userId = oauthRepository.getUserId(IdentityType.EMAIL, email);
		if (userId == null) {
			throw new LoginException("邮箱错误");
		}
		boolean checkPassword = passwordRepository.checkPassword(userId, password);
		if (!checkPassword) {
			throw new LoginException("密码错误");
		}
		return userId;
	}
}
