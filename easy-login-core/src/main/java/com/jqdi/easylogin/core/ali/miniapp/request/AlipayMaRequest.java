package com.jqdi.easylogin.core.ali.miniapp.request;

import java.util.Optional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.AlipayParser;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.parser.json.ObjectJsonParser;
import com.alipay.api.internal.util.AlipayEncrypt;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.jqdi.easylogin.core.ali.miniapp.model.MobileContentResponse;
import com.jqdi.easylogin.core.exception.LoginException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlipayMaRequest implements IAliMaRequest {
	private static final String PAY_URL = "https://openapi.alipay.com/gateway.do";
	private static final String SUCCESS_CODE = "10000";

	private String aesKey;
	private String appid;
	private String privateKey;
	private String publicKey;

	public AlipayMaRequest(String aesKey, String appid, String privateKey, String publicKey) {
		this.aesKey = aesKey;
		this.appid = appid;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	/**
	 * <pre>
	 * 官方文档：https://opendocs.alipay.com/mini/api/getphonenumber
	 * </pre>
	 */
	@Override
	public String getMobile(String encryptedData) {
		try {
			/**
			 * <pre>
			 * encryptedData: "d4E0LN/s9c/FWb5fhpReeGCOROpadHaK6mHHDMj1qKyI0S5RwxcNyUB5iaIwAxcZC/TeiDdrYAOvCoQmKskbWw=="
			 * </pre>
			 */
			String mobileContent = AlipayEncrypt.decryptContent(encryptedData, AlipayConstants.ENCRYPT_TYPE_AES, aesKey,
					AlipayConstants.CHARSET_UTF8);
			log.debug("mobileContent:{}", mobileContent);
			/**
			 * <pre>
			// 正常结果
			{
				"code": "10000",
				"msg": "Success",
				"mobile": "1597671905"
			}
			
			// 异常结果。解决方案：请参考接入必读第一步
			{
				"code": "40001",
				"msg": "Missing Required Arguments",
				"subCode": "isv.missing-encrypt-key",
				"subMsg": "缺少加密配置"
			}
			
			// 异常结果。解决方案：请参考接入必读第一步
			{
				"code": "40006", // 解决方案：请参考接入必读第二步
				"msg": "Insufficient Permissions",
				"subCode": "isv.insufficient-isv-permissions",
				"subMsg": "ISV权限不足，建议在开发者中心检查对应功能是否已经添加，解决办法详见：https://docs.open.alipay.com/common/isverror"
			}
			
			// 异常结果。解决方案：请参考接入必读第一步
			{
				"code": "40003", // 解决方案：请参考接入必读第三步
				"msg": "Insufficient Conditions",
				"subCode": "isv.invalid-auth-relations",
				"subMsg": "无效的授权关系"
			}
			
			// 异常结果。解决方案：请参考接入必读第一步
			{
				"code": "20000", // 解决方案：请稍后重试
				"msg": "Service Currently Unavailable",
				"subCode": "aop.unknow-error",
				"subMsg": "系统繁忙"
			}
			 * </pre>
			 */
			String wrapContent = String.format("{\"body\":%s}", mobileContent);// 支付宝json解析器只取第二层数据
			AlipayParser<MobileContentResponse> parser = new ObjectJsonParser<>(MobileContentResponse.class);
			MobileContentResponse response = parser.parse(wrapContent);

			if (!SUCCESS_CODE.equals(response.getCode())) {
				String message = Optional.ofNullable(response.getSubMsg()).orElse(response.getMsg());
				throw new LoginException(message);
			}

			return response.getMobile();
		} catch (AlipayApiException e) {
			log.error("getMobile error", e);
			String message = Optional.ofNullable(e.getErrMsg()).orElse(e.getMessage());
			throw new LoginException(message);
		}
	}

	/**
	 * <pre>
	 * 官方文档：https://opendocs.alipay.com/mini/api/openapi-authorize
	 * 前端获取authcode需要使用（auth_base：基本信息授权）
	 * </pre>
	 */
	@Override
	public String getUserId(String authcode) {
		try {
			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
			oauthTokenRequest.setGrantType("authorization_code");
			oauthTokenRequest.setCode(authcode);

			AlipayClient alipayClient = new DefaultAlipayClient(PAY_URL, appid, privateKey, AlipayConstants.FORMAT_JSON,
					AlipayConstants.CHARSET_UTF8, publicKey, AlipayConstants.SIGN_TYPE_RSA2);

			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
			log.debug("oauthTokenResponse:{}", oauthTokenResponse);

			if (!oauthTokenResponse.isSuccess()) {
				String message = oauthTokenResponse.getMsg();
				throw new LoginException(message);
			}

			return oauthTokenResponse.getUserId();
		} catch (AlipayApiException e) {
			log.error("getUserId error", e);
			String message = Optional.ofNullable(e.getErrMsg()).orElse(e.getMessage());
			throw new LoginException(message);
		}
	}
}