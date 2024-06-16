package com.jqdi.core;

import com.jqdi.core.repository.CacheOauthRepository;
import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.ali.miniapp.AlipayMiniappClient;
import com.jqdi.easylogin.core.ali.miniapp.request.AlipayMaRequest;
import com.jqdi.easylogin.core.ali.miniapp.request.IAliMaRequest;
import com.jqdi.easylogin.core.repository.OauthRepository;

public class AlipayMiniappClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();

		String aesKey = "321a3s12dsa";
		String appid = "51s3ad13sa1d";
		String privateKey = "LTAIkcl1bVhsEpGf";
		String publicKey = "13sa1d3s1adsadsdsd6sa51d651";

		IAliMaRequest aliMaRequest = new AlipayMaRequest(aesKey, appid, privateKey, publicKey);
		LoginClient loginClient = new AlipayMiniappClient(oauthRepository, aliMaRequest);

		String encryptedData = "aaaaaaaaa";
		String wxcode = "aaaaaa";

		String userId = loginClient.login(encryptedData, null, wxcode);
		System.out.println(userId);
	}
}