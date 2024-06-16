package com.jqdi.core;

import com.jqdi.core.repository.CacheOauthRepository;
import com.jqdi.core.repository.CacheVerifycodeRepository;
import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.mobile.MobileCodeClient;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.repository.VerifycodeRepository;

public class MobileCodeClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();

		VerifycodeRepository verifycodeRepository = new CacheVerifycodeRepository();
		LoginClient loginClient = new MobileCodeClient(oauthRepository, verifycodeRepository);

		String mobile = "15288888888";
		String code = "123456";
		String userId = loginClient.login(mobile, code, null);
		System.out.println(userId);
		
		mobile = "15288888889";
		code = "123456";
		userId = loginClient.login(mobile, code, null);
		System.out.println(userId);
	}
}