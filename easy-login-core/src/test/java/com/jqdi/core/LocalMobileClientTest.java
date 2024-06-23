package com.jqdi.core;

import com.jqdi.core.repository.CacheOauthRepository;
import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.mobile.LocalMobileClient;
import com.jqdi.easylogin.core.mobile.request.AliyunOneKeyLoginRequest;
import com.jqdi.easylogin.core.mobile.request.ILocalMobileRequest;
import com.jqdi.easylogin.core.repository.OauthRepository;

public class LocalMobileClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();

		String accessKeyId = "aaaaa";
		String accessKeySecret = "bbbbbb";
		String endpoint = "dypnsapi.aliyuncs.com";

		ILocalMobileRequest localMobileRequest = new AliyunOneKeyLoginRequest(accessKeyId, accessKeySecret, endpoint);
		LoginClient loginClient = new LocalMobileClient(oauthRepository, localMobileRequest);

		String accessToken = "aaaaaaaaaaaaa";
		String userId = loginClient.login(accessToken, null, null);
		System.out.println(userId);
	}
}