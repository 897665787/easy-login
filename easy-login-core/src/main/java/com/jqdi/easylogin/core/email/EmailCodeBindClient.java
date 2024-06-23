package com.jqdi.easylogin.core.email;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.constants.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.model.BindAuthCode;
import com.jqdi.easylogin.core.model.BindAuthCode.BindUserOauth;
import com.jqdi.easylogin.core.repository.VerifycodeRepository;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.repository.OauthTempRepository;

/**
 * 邮箱+验证码登录（并绑定第三方登录信息）
 * 
 * <pre>
 * 使用场景：微信APP、微信公众号登录过程中需要绑定邮箱，以邮箱作为系统用户必须的登录方式（如果是以手机号作为必须的登录方式，逻辑类似）
 * </pre>
 * 
 * @author JQ棣
 */
public class EmailCodeBindClient implements LoginClient {
	private OauthRepository oauthRepository;
	private OauthTempRepository oauthTempRepository;
	private VerifycodeRepository verifycodeRepository;

	public EmailCodeBindClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository,
			VerifycodeRepository verifycodeRepository) {
		this.oauthRepository = oauthRepository;
		this.oauthTempRepository = oauthTempRepository;
		this.verifycodeRepository = verifycodeRepository;
	}

	@Override
	public String login(String email, String code, String bindCode) {
		if (StringUtils.isBlank(email) || StringUtils.isBlank(code)) {
			throw new LoginException("缺失参数");
		}

		// 核对验证码
		boolean checkVerifyCode = verifycodeRepository.checkVerifycode(email, code);
		if (!checkVerifyCode) {
			throw new LoginException("验证码错误");
		}

		String userId = oauthRepository.getUserId(IdentityType.EMAIL, email);

		BindAuthCode mobileBindAuthCode = oauthTempRepository.getBindAuthCode(bindCode);

		if (userId == null) {// 账号不存在
			// 创建新用户
			String nickname = Optional.ofNullable(mobileBindAuthCode).map(BindAuthCode::getNickname).orElse(null);
			String avator = Optional.ofNullable(mobileBindAuthCode).map(BindAuthCode::getHeadimgurl).orElse(null);

			userId = oauthRepository.registerUser(IdentityType.EMAIL, email, nickname, avator);
		}

		// 绑定authcode
		if (mobileBindAuthCode != null) {
			List<BindUserOauth> binds = mobileBindAuthCode.getBinds();
			for (BindUserOauth bind : binds) {
				String identifier = bind.getIdentifier();
				if (StringUtils.isNotBlank(identifier)) {
					oauthRepository.bindOauth(userId, bind.getIdentityType(), identifier, bindCode);
				}
			}
		}
		return userId;
	}
}
