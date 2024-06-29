package com.jqdi.core;

import com.jqdi.core.repository.CacheOauthRepository;
import com.jqdi.core.repository.CacheTempOauthRepository;
import com.jqdi.core.repository.CacheVerifycodeRepository;
import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.mobile.MobileCodeBindClient;
import com.jqdi.easylogin.core.qq.QQClient;
import com.jqdi.easylogin.core.qq.request.APIRequest;
import com.jqdi.easylogin.core.qq.request.IQQRequest;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.repository.OauthTempRepository;
import com.jqdi.easylogin.core.repository.VerifycodeRepository;

public class QQClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();
		OauthTempRepository oauthTempRepository = new CacheTempOauthRepository();

		String appid = "51s3ad13sa1d";
		String appkey = "LTAIkcl1bVhsEpGf";

		IQQRequest qqRequest = new APIRequest(appid, appkey);

//		qqRequest.token("test", "test");
//		qqRequest.getOpenid("test");
//		qqRequest.getUserInfo("test", "test");
		
		LoginClient loginClient = new QQClient(oauthRepository, oauthTempRepository, qqRequest);

		String redirectUri = "http://open.xxx.com/openapi/callback";
		String code = "aaaaaa";
		String userId = loginClient.login(null, redirectUri, code);
		System.out.println(userId);// 是null代表要绑定账号

		VerifycodeRepository verifycodeRepository = new CacheVerifycodeRepository();
		loginClient = new MobileCodeBindClient(oauthRepository, oauthTempRepository, verifycodeRepository);

		String mobile = "15288888888";
		String verifycode = "123456";
		String authcode = code;
		userId = loginClient.login(mobile, verifycode, authcode);
		System.out.println(userId);
	}
}