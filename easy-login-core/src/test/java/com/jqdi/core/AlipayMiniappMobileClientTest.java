package com.jqdi.core;

import com.jqdi.core.repository.CacheOauthRepository;
import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.ali.miniappmobile.AlipayMiniappMobileClient;
import com.jqdi.easylogin.core.ali.miniappmobile.request.AlipayMaMobileRequest;
import com.jqdi.easylogin.core.ali.miniappmobile.request.IAlipayMaMobileRequest;
import com.jqdi.easylogin.core.repository.OauthRepository;

public class AlipayMiniappMobileClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();

		String appid = "51s3ad13sa1d";
		String privateKey = "LTAIkcl1bVhsEpGf";
		String publicKey = "13sa1d3s1adsadsdsd6sa51d651";

		IAlipayMaMobileRequest alipayMaMobileRequest = new AlipayMaMobileRequest(appid, privateKey, publicKey);
		LoginClient loginClient = new AlipayMiniappMobileClient(oauthRepository, alipayMaMobileRequest);

		String authcode = "aaaaaa";

		String userId = loginClient.login(null, null, authcode);
		System.out.println(userId);
	}
}