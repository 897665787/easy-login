package com.jqdi.core;

import com.jqdi.core.repository.CacheOauthRepository;
import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.wx.miniapp.WeixinMiniappClient;
import com.jqdi.easylogin.core.wx.miniapp.request.BinarywangMaRequest;
import com.jqdi.easylogin.core.wx.miniapp.request.IMaRequest;

public class WeixinMiniappClientTest {

	public static void main(String[] args) throws Exception {
		OauthRepository oauthRepository = new CacheOauthRepository();
		
		String appid = "51s3ad13sa1d";
		String secret = "LTAIkcl1bVhsEpGf";

		IMaRequest maRequest = new BinarywangMaRequest(appid, secret);
		LoginClient loginClient = new WeixinMiniappClient(oauthRepository, maRequest);

		String encryptedData = "aaaaaaaaa";
		String iv = "aaaaa";
		String wxcode = "aaaaaa";
		String userId = loginClient.login(encryptedData, iv, wxcode);
		System.out.println(userId);
	}
}