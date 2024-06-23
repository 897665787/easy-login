package com.jqdi.easylogin.core.mobile.request;

import com.aliyun.dypnsapi20170525.Client;
import com.aliyun.dypnsapi20170525.models.GetMobileRequest;
import com.aliyun.dypnsapi20170525.models.GetMobileResponse;
import com.aliyun.dypnsapi20170525.models.GetMobileResponseBody;
import com.aliyun.dypnsapi20170525.models.GetMobileResponseBody.GetMobileResponseBodyGetMobileResultDTO;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.jqdi.easylogin.core.exception.LoginException;

import lombok.extern.slf4j.Slf4j;

/**
 * 阿里云一键登录API实现
 * 
 * @author JQ棣
 */
@Slf4j
public class AliyunOneKeyLoginRequest implements ILocalMobileRequest {
	private static final Integer STATUS_CODE_SUCCESS = 0;
	private static final String BODY_CODE_SUCCESS = "OK";

	private Client client;

	public AliyunOneKeyLoginRequest(String accessKeyId, String accessKeySecret, String endpoint) {
		Config config = new Config().setAccessKeyId(accessKeyId).setAccessKeySecret(accessKeySecret)
				.setEndpoint(endpoint);
		try {
			client = new Client(config);
		} catch (Exception e) {
			log.error("init Client error", e);
		}
	}

	/**
	 * 获取手机号
	 * 
	 * <pre>
	 * 官网：https://help.aliyun.com/zh/pnvs/developer-reference/api-dypnsapi-2017-05-25-getmobile
	 * </pre>
	 */
	@Override
	public String getMobile(String accessToken) {
		try {
			GetMobileRequest request = new GetMobileRequest().setAccessToken(accessToken);
			GetMobileResponse response = client.getMobileWithOptions(request, new RuntimeOptions());
			log.debug("GetMobileResponse:{}", response);
			/**
			 * <pre>
			{
				"Code": "OK",
				"Message": "请求成功",
				"RequestId": "8906582E-6722",
				"GetMobileResultDTO": {
					"Mobile": "13900001234"
				}
			}
			 * </pre>
			 */
			
			Integer statusCode = response.getStatusCode();
			if (STATUS_CODE_SUCCESS.equals(statusCode)) {
				throw new LoginException("StatusCode error:" + statusCode);
			}

			GetMobileResponseBody body = response.getBody();
			String code = body.getCode();
			if (!BODY_CODE_SUCCESS.equals(code)) {
				throw new LoginException(body.getMessage());
			}
			GetMobileResponseBodyGetMobileResultDTO resultDTO = body.getGetMobileResultDTO();

			return resultDTO.getMobile();
		} catch (TeaException e) {
			log.error("getMobile error", e);
			throw new LoginException(e.getMessage());
		} catch (Exception e) {
			log.error("getMobile error", e);
			throw new LoginException(e.getMessage());
		}
	}
}
