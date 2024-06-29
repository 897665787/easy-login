package com.jqdi.easylogin.core.qq;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.constants.IdentityType;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.model.BindAuthCode;
import com.jqdi.easylogin.core.model.BindAuthCode.BindUserOauth;
import com.jqdi.easylogin.core.qq.model.TokenOpenid;
import com.jqdi.easylogin.core.qq.model.UserInfo;
import com.jqdi.easylogin.core.qq.request.IQQRequest;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.repository.OauthTempRepository;

/**
 * QQ授权登录
 * 
 * @author JQ棣
 */
public class QQClient implements LoginClient {
	private OauthRepository oauthRepository;
	private OauthTempRepository oauthTempRepository;
	private IQQRequest qqRequest;

	public QQClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository, IQQRequest qqRequest) {
		this.oauthRepository = oauthRepository;
		this.oauthTempRepository = oauthTempRepository;
		this.qqRequest = qqRequest;
	}

	/**
	 * <pre>
	 * 官网（Step2：通过Authorization Code获取Access Token）：https://wiki.connect.qq.com/%e4%bd%bf%e7%94%a8authorization_code%e8%8e%b7%e5%8f%96access_token
	 * </pre>
	 */
	@Override
	public String login(String ignore1, String redirectUri, String code) {
		if (StringUtils.isBlank(code)) {
			throw new LoginException("缺失参数");
		}

		TokenOpenid tokenOpenid = qqRequest.token(code, redirectUri);
		String openid = tokenOpenid.getOpenid();
		if (StringUtils.isBlank(openid)) {// token接口拿不到openid
			openid = qqRequest.getOpenid(tokenOpenid.getAccessToken());
		}

		UserInfo userInfo = qqRequest.getUserInfo(tokenOpenid.getAccessToken(), openid);

		// 根据openid查询user_id
		String userId = oauthRepository.getUserId(IdentityType.QQ_OPENID, openid);

		if (userId == null) {
			// 存储BindAuthCode相关信息
			BindAuthCode bindAuthCode = new BindAuthCode();
			bindAuthCode.setNickname(Optional.ofNullable(userInfo).map(UserInfo::getNickname).orElse(null));
			bindAuthCode.setHeadimgurl(Optional.ofNullable(userInfo).map(UserInfo::getHeadimgurl).orElse(null));
			List<BindUserOauth> binds = Lists.newArrayList();
			binds.add(new BindUserOauth().setIdentityType(IdentityType.QQ_OPENID).setIdentifier(openid));
			bindAuthCode.setBinds(binds);

			oauthTempRepository.saveBindAuthCode(code, bindAuthCode);

			// QQ没有绑定账号
			return null;
		}

		return userId;
	}
}
