package com.jqdi.core;

import com.jqdi.core.repository.CacheOauthRepository;
import com.jqdi.core.repository.CacheTempOauthRepository;
import com.jqdi.core.repository.CacheVerifycodeRepository;
import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.google.GoogleClient;
import com.jqdi.easylogin.core.google.request.APIRequest;
import com.jqdi.easylogin.core.google.request.IGoogleRequest;
import com.jqdi.easylogin.core.mobile.MobileCodeBindClient;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.repository.OauthTempRepository;
import com.jqdi.easylogin.core.repository.VerifycodeRepository;

public class GoogleClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();
		OauthTempRepository oauthTempRepository = new CacheTempOauthRepository();

		String clientId = "288122398339-acsrn1si5xmhnv90qqu90.apps.googleusercontent.com";
		String clientSecret = "_ArbOxauyr1cAiDA";

		IGoogleRequest qqRequest = new APIRequest(clientId, clientSecret);
		LoginClient loginClient = new GoogleClient(oauthRepository, oauthTempRepository, qqRequest);

		String redirectUri = "www.qq.com/my.php";
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