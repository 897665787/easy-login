package com.jqdi.easylogin.core.wx.mp.request;

import java.util.Map;

import com.google.common.collect.Maps;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.wx.mp.model.MpAccessToken;
import com.jqdi.easylogin.core.wx.mp.model.MpUserInfo;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

/**
 * Binarywang SDK实现
 * 
 * @author JQ棣
 */
@Slf4j
public class BinarywangMpRequest implements IMpRequest {

	private WxMpService wxMpService;

	public BinarywangMpRequest(String appid, String secret, String token, String aesKey) {
		WxMpDefaultConfigImpl config = new WxMpDefaultConfigImpl();
		config.setAppId(appid);
		config.setSecret(secret);
		config.setToken(token);
		config.setAesKey(aesKey);

		wxMpService = new WxMpServiceImpl();

		Map<String, WxMpConfigStorage> configStorage = Maps.newHashMap();
		configStorage.put(appid, config);
		wxMpService.setMultiConfigStorages(configStorage);
	}

	/**
	 * <pre>
	 * 官网：https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
	 * </pre>
	 */
	@Override
	public MpAccessToken getAccessToken(String code) {
		MpAccessToken mpAccessToken = new MpAccessToken();
		try {
			WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
			log.debug("WxOAuth2AccessToken:{}", accessToken);
			/**
			 * <pre>
			{
				"access_token": "58_hutKyhcT5biJsn04NZ9jHW9GZVgCVOF4YVlK3NZiivKq8rJkWs2-la-nhJNhuisNs-fwFtfeaIyOuWNYC9AOW9fzv3oz_pMcg_zUYoZplZc",
				"expires_in": 7200,
				"refresh_token": "58_luzGuma7ZudDzx4kF1-YCo9wNUI8D9-FWDLCtGg6idqwBy7yJ2ssAMpjgNR-uQz603Mrm2gadX5B1XRHZ76gEMooN-T2Fn514skqOWTOWMM",
				"openid": "oYB7c6mHjNn4LW_zXX121R8mVSJ8",
				"scope": "snsapi_userinfo"
			}
			 * </pre>
			 */

			mpAccessToken.setAccessToken(accessToken.getAccessToken());
			mpAccessToken.setOpenid(accessToken.getOpenId());
			mpAccessToken.setUnionid(accessToken.getUnionId());
		} catch (WxErrorException e) {
			log.error("getAccessToken error", e);
			WxError error = e.getError();
			throw new LoginException(error.getErrorMsg());
		}
		return mpAccessToken;
	}

	/**
	 * <pre>
	 * 官网：https://api.weixin.qq.com/sns/userinfo?access_token=58_hutKyhcT5biJsn04NZ9jHW9GZVgCVOF4YVlK3NZiivKq8rJkWs2-la-nhJNhuisNs-fwFtfeaIyOuWNYC9AOW9fzv3oz_pMcg_zUYoZplZc&openid=oYB7c6mHjNn4LW_zXXPsDR8mVSJ8
	 * </pre>
	 */
	@Override
	public MpUserInfo getUserinfo(String accessToken, String openid) {
		MpUserInfo mpUserInfo = new MpUserInfo();
		try {
			WxOAuth2AccessToken wxOAuth2AccessToken = new WxOAuth2AccessToken();
			wxOAuth2AccessToken.setAccessToken(accessToken);
			wxOAuth2AccessToken.setOpenId(openid);

			WxOAuth2UserInfo user = wxMpService.getOAuth2Service().getUserInfo(wxOAuth2AccessToken, "zh_CN");
			log.debug("WxOAuth2UserInfo:{}", user);
			/**
			 * <pre>
			{
				"openid": "oYB7c6mHjNn4LW_zXXPsDR8mVSJ8",
				"nickname": "勝",
				"sex": 0,
				"language": "",
				"city": "",
				"province": "",
				"country": "",
				"headimgurl": "https://thirdwx.qlogo.cn/mmopen/vi_32/4gV1O4eXicKuKlmuG5aniaNadRm3hkbbdp5qRd8MMY946ia1eDiah2ttfFeLRp8CEn5HFda6AKCM67GWbDj11dzy1Q/132",
				"privilege": [],
				"unionid": "oiPIJuEo0OzxLqzSEWZYZ-nVWmTU"
			}
			 * </pre>
			 */

			mpUserInfo.setOpenid(user.getOpenid());
			mpUserInfo.setNickname(user.getNickname());
			mpUserInfo.setHeadimgurl(user.getHeadImgUrl());
			mpUserInfo.setUnionid(user.getUnionId());
		} catch (WxErrorException e) {
			log.error("getUserInfo error", e);
			WxError error = e.getError();
			throw new LoginException(error.getErrorMsg());
		}
		return mpUserInfo;
	}
}