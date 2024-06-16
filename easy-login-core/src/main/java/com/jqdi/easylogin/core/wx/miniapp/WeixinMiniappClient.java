package com.jqdi.easylogin.core.wx.miniapp;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.enums.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.wx.miniapp.model.MaSession;
import com.jqdi.easylogin.core.wx.miniapp.model.MaSessionPhoneNumber;
import com.jqdi.easylogin.core.wx.miniapp.request.IMaRequest;

public class WeixinMiniappClient implements LoginClient {
	private OauthRepository oauthRepository;
	private IMaRequest maRequest;

	public WeixinMiniappClient(OauthRepository oauthRepository, IMaRequest maRequest) {
		this.oauthRepository = oauthRepository;
		this.maRequest = maRequest;
	}

	@Override
	public String login(String encryptedData, String iv, String wxcode) {
		if (StringUtils.isBlank(encryptedData) || StringUtils.isBlank(iv) || StringUtils.isBlank(wxcode)) {
			throw new LoginException("缺失参数");
		}

		MaSessionPhoneNumber maSessionPhoneNumber = maRequest.getSessionInfoAndPhoneNumber(encryptedData, iv, wxcode);
		MaSession maSession = maSessionPhoneNumber.getMaSession();

		String mobile = maSessionPhoneNumber.getPhoneNumber();
		
		String userId = oauthRepository.getUserId(IdentityType.MOBILE, mobile);
		if (userId == null) {// 账号不存在
			// 创建新用户
			userId = oauthRepository.registerUser(IdentityType.MOBILE, mobile, null, null);
		}

		// 绑定用户与openid,unionid关系
		String openid = maSession.getOpenid();
		oauthRepository.bindOauth(userId, IdentityType.WX_OPENID_MINIAPP, openid, wxcode);
		String unionid = maSession.getUnionid();
		if (StringUtils.isNotBlank(unionid)) {
			oauthRepository.bindOauth(userId, IdentityType.WX_UNIONID, unionid, wxcode);
		}

		return userId;
	}
}
