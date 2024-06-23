package com.jqdi.easylogin.core.wx.mp;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.constants.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.model.BindAuthCode;
import com.jqdi.easylogin.core.model.BindAuthCode.BindUserOauth;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.repository.OauthTempRepository;
import com.jqdi.easylogin.core.wx.mp.model.MpAccessToken;
import com.jqdi.easylogin.core.wx.mp.model.MpUserInfo;
import com.jqdi.easylogin.core.wx.mp.request.IMpRequest;

/**
 * 公众号微信授权登录
 * 
 * @author JQ棣
 */
public class WeixinMpClient implements LoginClient {
	private OauthRepository oauthRepository;
	private OauthTempRepository oauthTempRepository;
	private IMpRequest mpRequest;

	public WeixinMpClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository,
			IMpRequest mpRequest) {
		this.oauthRepository = oauthRepository;
		this.oauthTempRepository = oauthTempRepository;
		this.mpRequest = mpRequest;
	}

	/**
	 * <pre>
	 * 官网：https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
	 * </pre>
	 */
	@Override
	public String login(String ignore1, String ignore2, String wxcode) {
		if (StringUtils.isBlank(wxcode)) {
			throw new LoginException("缺失参数");
		}

		MpAccessToken mpAccessToken = mpRequest.getAccessToken(wxcode);

		String openid = mpAccessToken.getOpenid();
		String accessToken = mpAccessToken.getAccessToken();
		String unionid = mpAccessToken.getUnionid();// 可能没有unionid，需要通过userinfo获取
		MpUserInfo userinfo = null;
		if (StringUtils.isBlank(unionid)) {
			userinfo = mpRequest.getUserinfo(accessToken, openid);
			unionid = userinfo.getUnionid();
		}

		// 根据openid查询user_id
		String userId = oauthRepository.getUserId(IdentityType.WX_OPENID_MP, openid);
		if (userId == null) {
			// 根据unionid查询user_id
			if (StringUtils.isNotBlank(unionid)) {// 未绑定微信开放平台是没有unionid的
				userId = oauthRepository.getUserId(IdentityType.WX_UNIONID, unionid);
			}
			if (userId != null) {
				// unionid已经找到用户，但是openid没找到用户，自动做绑定
				oauthRepository.bindOauth(userId, IdentityType.WX_OPENID_MP, openid, wxcode);
			}
		} else {
			// openid已经找到用户，如果unionid没找到用户的话自动做绑定
			if (StringUtils.isNotBlank(unionid)) {// 未绑定微信开放平台是没有unionid的
				String userId2 = oauthRepository.getUserId(IdentityType.WX_UNIONID, unionid);
				if (userId2 == null) {
					oauthRepository.bindOauth(userId, IdentityType.WX_UNIONID, unionid, wxcode);
				}
			}
		}

		if (userId == null) {
			// 说明微信没有绑定账号，需前端跳转到手机号+验证码登录(MOBILE_CODE_BIND)，并携带信息可以方便后续找到openid，unionid和用户信息
			if (userinfo == null) {
				userinfo = mpRequest.getUserinfo(accessToken, openid);
			}
			// 存储MobileBindAuthCode相关信息
			BindAuthCode mobileBindAuthCode = new BindAuthCode();
			mobileBindAuthCode.setNickname(Optional.ofNullable(userinfo).map(MpUserInfo::getNickname).orElse(null));
			mobileBindAuthCode.setHeadimgurl(Optional.ofNullable(userinfo).map(MpUserInfo::getHeadimgurl).orElse(null));
			List<BindUserOauth> binds = Lists.newArrayList();
			binds.add(new BindUserOauth().setIdentityType(IdentityType.WX_OPENID_MP).setIdentifier(openid));
			binds.add(new BindUserOauth().setIdentityType(IdentityType.WX_UNIONID).setIdentifier(unionid));
			mobileBindAuthCode.setBinds(binds);
			oauthTempRepository.saveBindAuthCode(wxcode, mobileBindAuthCode);

			// 微信没有绑定账号
			return null;
		}

		// 通过解密微信的密文获取手机号码，可直接使用
		return userId;
	}
}