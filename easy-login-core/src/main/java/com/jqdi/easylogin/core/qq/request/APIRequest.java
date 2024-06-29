package com.jqdi.easylogin.core.qq.request;

import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.qq.model.TokenOpenid;
import com.jqdi.easylogin.core.qq.model.UserInfo;
import com.jqdi.easylogin.core.qq.request.model.MeResp;
import com.jqdi.easylogin.core.qq.request.model.TokenResp;
import com.jqdi.easylogin.core.qq.request.model.UserInfoResp;
import com.jqdi.easylogin.core.util.HttpUtil;
import com.jqdi.easylogin.core.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * API 实现
 * 
 * @author JQ棣
 */
@Slf4j
public class APIRequest implements IQQRequest {

	private String appid;
	private String appkey;

	public APIRequest(String appid, String appkey) {
		this.appid = appid;
		this.appkey = appkey;
	}

	/**
	 * <pre>
	 * 官网：https://wiki.connect.qq.com/%e4%bd%bf%e7%94%a8authorization_code%e8%8e%b7%e5%8f%96access_token
	 * </pre>
	 */
	@Override
	public TokenOpenid token(String code, String redirectUri) {
		try {
			String reqUrl = "https://graph.qq.com/oauth2.0/token?fmt=json&need_openid=1&grant_type=authorization_code&client_id="
					+ appid + "&client_secret=" + appkey + "&code=" + code + "&redirect_uri="
					+ URLEncoder.encode(redirectUri, "utf-8");
			log.debug("reqUrl,{}", reqUrl);

			String jsonResponse = HttpUtil.get(reqUrl);
			log.debug("jsonResponse:{}", jsonResponse);
			/**
			 * <pre>
			{
				"error": 100001,
				"error_description": "param client_id is wrong or lost"
			}
			 * </pre>
			 */

			TokenResp tokenResp = JsonUtil.toEntity(jsonResponse, TokenResp.class);
			if (tokenResp.getError() != null && tokenResp.getError() != 0) {
				throw new LoginException(tokenResp.getErrorDescription());
			}

			TokenOpenid tokenOpenid = new TokenOpenid();
			tokenOpenid.setAccessToken(tokenResp.getAccessToken());
			tokenOpenid.setOpenid(tokenResp.getOpenid());// 官方文档说参数need_openid可以传回openid，不确定
			return tokenOpenid;
		} catch (Exception e) {
			log.error("request error", e);
			throw new LoginException(e.getMessage());
		}
	}

	/**
	 * <pre>
	 * 官网：https://wiki.connect.qq.com/%e8%8e%b7%e5%8f%96%e7%94%a8%e6%88%b7openid_oauth2-0
	 * </pre>
	 */
	@Override
	public String getOpenid(String accessToken) {
		try {
			String reqUrl = "https://graph.qq.com/oauth2.0/me?fmt=json&access_token=" + accessToken;
			log.debug("reqUrl,{}", reqUrl);

			String jsonResponse = HttpUtil.get(reqUrl);
			log.debug("jsonResponse:{}", jsonResponse);
			/**
			 * <pre>
			{
				"error": 100001,
				"error_description": "param client_id is wrong or lost"
			}
			{
				"client_id": "YOUR_APPID",
				"openid": "YOUR_OPENID"
			}
			 * </pre>
			 */

			MeResp meResp = JsonUtil.toEntity(jsonResponse, MeResp.class);
			if (meResp.getError() != null && meResp.getError() != 0) {
				throw new LoginException(meResp.getErrorDescription());
			}
			return meResp.getOpenid();
		} catch (Exception e) {
			log.error("request error", e);
			throw new LoginException(e.getMessage());
		}
	}

	/**
	 * <pre>
	 * 官网：https://wiki.connect.qq.com/get_user_info
	 * </pre>
	 */
	@Override
	public UserInfo getUserInfo(String accessToken, String openid) {
		try {
			String reqUrl = "https://graph.qq.com/user/get_user_info?access_token=" + accessToken
					+ "&oauth_consumer_key=" + appid + "&openid=" + openid;
			log.debug("reqUrl,{}", reqUrl);

			String jsonResponse = HttpUtil.get(reqUrl);
			log.debug("jsonResponse:{}", jsonResponse);
			/**
			 * <pre>
			{
				"ret": 1002,
				"msg": "请先登录"
			}
			{
				"ret": 0,
				"msg": "",
				"nickname": "Peter",
				"figureurl": "http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/30",
				"figureurl_1": "http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/50",
				"figureurl_2": "http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/100",
				"figureurl_qq_1": "http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/40",
				"figureurl_qq_2": "http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/100",
				"gender": "男"
			}
			 * </pre>
			 */

			UserInfoResp userInfoResp = JsonUtil.toEntity(jsonResponse, UserInfoResp.class);
			if (userInfoResp.getRet() != null && userInfoResp.getRet() != 0) {
				throw new LoginException(userInfoResp.getMsg());
			}

			UserInfo userInfo = new UserInfo();
			userInfo.setNickname(userInfoResp.getNickname());
			String headimgurl = userInfoResp.getFigureurlQq2();
			if (StringUtils.isBlank(headimgurl)) {
				headimgurl = userInfoResp.getFigureurlQq1();
			}
			if (StringUtils.isBlank(headimgurl)) {
				headimgurl = userInfoResp.getFigureurl2();
			}
			if (StringUtils.isBlank(headimgurl)) {
				headimgurl = userInfoResp.getFigureurl1();
			}
			if (StringUtils.isBlank(headimgurl)) {
				headimgurl = userInfoResp.getFigureurl();
			}
			userInfo.setHeadimgurl(headimgurl);
			return userInfo;
		} catch (Exception e) {
			log.error("request error", e);
			throw new LoginException(e.getMessage());
		}
	}
}
