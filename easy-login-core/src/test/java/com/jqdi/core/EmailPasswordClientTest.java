package com.jqdi.core;

import com.jqdi.core.repository.CacheOauthRepository;
import com.jqdi.core.repository.CachePasswordRepository;
import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.password.EmailPasswordClient;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.repository.PasswordRepository;

public class EmailPasswordClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();

		PasswordRepository passwordRepository = new CachePasswordRepository();
		LoginClient loginClient = new EmailPasswordClient(oauthRepository, passwordRepository);

		String email = "6666666@qq.com";
		String password = "aaaaaaaaaaaaa";
		String userId = loginClient.login(email, password, null);
		System.out.println(userId);
	}
}