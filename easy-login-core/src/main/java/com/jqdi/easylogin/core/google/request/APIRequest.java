package com.jqdi.easylogin.core.google.request;

import java.io.IOException;
import java.net.URI;
import java.util.Date;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.GenericData;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.ClientId;
import com.google.auth.oauth2.UserAuthorizer;
import com.google.auth.oauth2.UserCredentials;
import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.qq.request.model.TokenResp;
import com.jqdi.easylogin.core.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * API 实现
 * 
 * @author JQ棣
 */
@Slf4j
public class APIRequest implements IGoogleRequest {

	private UserAuthorizer userAuthorizer;

	public APIRequest(String clientId, String clientSecret) {
		ClientId clientId0 = ClientId.newBuilder().setClientId(clientId).setClientSecret(clientSecret).build();

		userAuthorizer = UserAuthorizer.newBuilder().setClientId(clientId0).build();
	}

	/**
	 * <pre>
	 * 官网：https://wiki.connect.qq.com/%e4%bd%bf%e7%94%a8authorization_code%e8%8e%b7%e5%8f%96access_token
	 * </pre>
	 */
	@Override
	public String getOpenid(String code, String redirectUri) {
		try {
			URI uri = URI.create(redirectUri);
			UserCredentials userCredentials = userAuthorizer.getCredentialsFromCode(code, uri);
			AccessToken accessToken = userCredentials.getAccessToken();
			
//			String reqUrl = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + appid
//					+ "&client_secret=" + appkey + "&code=" + code + "&redirect_uri="
//					+ URLEncoder.encode(redirectUri, "utf-8") + "&fmt=json&need_openid=1";
//			log.debug("reqUrl,{}", reqUrl);

//			String jsonResponse = HttpUtil.get("");
			log.debug("accessToken:{}", accessToken.toString());
			/**
			 * <pre>
			{
				"error": 100001,
				"error_description": "param client_id is wrong or lost"
			}
			 * </pre>
			 */

			TokenResp tokenResp = JsonUtil.toEntity("{}", TokenResp.class);
			if (tokenResp.getError() != null && tokenResp.getError() != 0) {
				throw new LoginException(tokenResp.getErrorDescription());
			}
			return tokenResp.getOpenid();
		} catch (Exception e) {
			log.error("request error", e);
			throw new LoginException(e.getMessage());
		}
	}

	public UserCredentials getUserinfo(AccessToken accessToken) throws IOException {
//		Preconditions.checkNotNull(code);
//		URI resolvedCallbackUri = getCallbackUri(baseUri);

		GenericData tokenData = new GenericData();
		tokenData.put("alt", "json");
		tokenData.put("access_token", accessToken.getTokenValue());
		
		UrlEncodedContent tokenContent = new UrlEncodedContent(tokenData);
//		HttpRequestFactory requestFactory = transportFactory.create().createRequestFactory();
		HttpTransport httpTransport = new NetHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		
//		URI tokenServerUri = URI.create("https://oauth2.googleapis.com/token");
		URI tokenServerUri = URI.create("https://www.googleapis.com/oauth2/v1/userinfo");
		HttpRequest tokenRequest = requestFactory.buildPostRequest(new GenericUrl(tokenServerUri), tokenContent);
		JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
		tokenRequest.setParser(new JsonObjectParser(jsonFactory));

		HttpResponse tokenResponse = tokenRequest.execute();
		/**
		{
			"id": "115304811039070037277",
			"name": "xuelei lin",
			"given_name": "xuelei",
			"family_name": "lin",
			"picture": "https://lh3.googleusercontxpiT_enTQ8GU0us5CkPgkh4bP8VR4vLpN0q",
			"locale": "zh-CN"
		}
		 */
		GenericJson parsedTokens = tokenResponse.parseAs(GenericJson.class);
		System.out.println(parsedTokens);
		
//		String accessTokenValue = OAuth2Utils.validateString(parsedTokens, "access_token", FETCH_TOKEN_ERROR);
//		int expiresInSecs = OAuth2Utils.validateInt32(parsedTokens, "expires_in", FETCH_TOKEN_ERROR);
//		Date expirationTime = new Date(new Date().getTime() + expiresInSecs * 1000);
//		String scopes = OAuth2Utils.validateOptionalString(parsedTokens, OAuth2Utils.TOKEN_RESPONSE_SCOPE,
//				FETCH_TOKEN_ERROR);
//		AccessToken accessToken = AccessToken.newBuilder().setExpirationTime(expirationTime)
//				.setTokenValue(accessTokenValue).setScopes(scopes).build();
//		String refreshToken = OAuth2Utils.validateOptionalString(parsedTokens, "refresh_token", FETCH_TOKEN_ERROR);

//		return UserCredentials.newBuilder().setClientId(clientId.getClientId())
//				.setClientSecret(clientId.getClientSecret()).setRefreshToken(refreshToken).setAccessToken(accessToken)
//				.setHttpTransportFactory(transportFactory).setTokenServerUri(tokenServerUri).build();
		return null;
	}

}
