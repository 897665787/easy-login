package com.jqdi.easylogin.core.ali.miniappmobile.request;

import java.util.Optional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.jqdi.easylogin.core.ali.miniappmobile.model.AliMobileUserId;
import com.jqdi.easylogin.core.exception.LoginException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlipayMaMobileRequest implements IAliMaMobileRequest {
	private static final String PAY_URL = "https://openapi.alipay.com/gateway.do";

	private String appid;
	private String privateKey;
	private String publicKey;

	public AlipayMaMobileRequest(String appid, String privateKey, String publicKey) {
		this.appid = appid;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	/**
	 * <pre>
	 * 官方文档：https://opendocs.alipay.com/mini/api/openapi-authorize
	 * 前端获取authcode需要使用（auth_user：会员信息授权）
	 * </pre>
	 */
	@Override
	public AliMobileUserId getPhoneNumber(String code) {
		try {

			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
			oauthTokenRequest.setGrantType("authorization_code");
			oauthTokenRequest.setCode(code);

			AlipayClient alipayClient = new DefaultAlipayClient(PAY_URL, appid, privateKey, AlipayConstants.FORMAT_JSON,
					AlipayConstants.CHARSET_UTF8, publicKey, AlipayConstants.SIGN_TYPE_RSA2);

			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
			log.debug("oauthTokenResponse:{}", oauthTokenResponse);

			AliMobileUserId aliMobileUserId = new AliMobileUserId();
			if (!oauthTokenResponse.isSuccess()) {
				String message = oauthTokenResponse.getMsg();
				throw new LoginException(message);
			}

			String accessToken = oauthTokenResponse.getAccessToken();

			AlipayUserInfoShareRequest shareRequest = new AlipayUserInfoShareRequest();
			AlipayUserInfoShareResponse shareResponse = alipayClient.execute(shareRequest, accessToken);
			log.debug("shareResponse:{}", shareResponse);

			if (!shareResponse.isSuccess()) {
				String message = oauthTokenResponse.getMsg();
				throw new LoginException(message);
			}
			aliMobileUserId.setMobile(shareResponse.getMobile());
			aliMobileUserId.setUserId(oauthTokenResponse.getUserId());
			return aliMobileUserId;
		} catch (AlipayApiException e) {
			log.error("getPhoneNumber error", e);
			String message = Optional.ofNullable(e.getErrMsg()).orElse(e.getMessage());
			throw new LoginException(message);
		}
	}
}