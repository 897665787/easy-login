package com.jqdi.easylogin.core.ali.miniappmobile;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.ali.miniappmobile.model.AliMobileUserId;
import com.jqdi.easylogin.core.ali.miniappmobile.request.IAlipayMaMobileRequest;
import com.jqdi.easylogin.core.constants.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.repository.OauthRepository;

/**
 * 支付宝小程序授权登录方式二（本质是拿到手机号登录）
 * 
 * @author JQ棣
 */
public class AlipayMiniappMobileClient implements LoginClient {
	private OauthRepository oauthRepository;
	private IAlipayMaMobileRequest alipayMaMobileRequest;
	
	public AlipayMiniappMobileClient(OauthRepository oauthRepository, IAlipayMaMobileRequest alipayMaMobileRequest) {
		this.oauthRepository = oauthRepository;
		this.alipayMaMobileRequest = alipayMaMobileRequest;
	}
	
	@Override
	public String login(String ignore1, String ignore2, String authcode) {
		if (StringUtils.isBlank(authcode)) {
			throw new LoginException("缺失参数");
		}

		AliMobileUserId aliMobileUserId = alipayMaMobileRequest.getPhoneNumber(authcode);

		String mobile = aliMobileUserId.getMobile();
		
		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		if (userId == null) {// 账号不存在
			// 创建新用户
			userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile, null, null);
		}
		
		// 绑定用户与支付宝userId关系
		oauthRepository.bindOauth(userId, IdentityType.ALI_USERID_MINIAPP, aliMobileUserId.getUserId(), authcode);
		
		return userId;
	}
}
