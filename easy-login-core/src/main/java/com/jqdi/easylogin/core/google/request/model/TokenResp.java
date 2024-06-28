package com.jqdi.easylogin.core.google.request.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TokenResp {
	/**
	 * 错误码，0代表成功
	 */
	@JsonProperty("error")
	private Integer error;
	/**
	 * 错误信息 
	 */
	@JsonProperty("error_description")
	private String errorDescription;
	
	/**
	 * 授权令牌，Access_Token
	 */
	@JsonProperty("access_token")
	private String accessToken;
	/**
	 * 该access token的有效期，单位为秒
	 */
	@JsonProperty("expires_in")
	private Integer expiresIn;
	/**
	 * 在授权自动续期步骤中，获取新的Access_Token时需要提供的参数。 注：refresh_token仅一次有效
	 */
	@JsonProperty("refresh_token")
	private String refreshToken;
	/**
	 * need_openid=1，表示同时获取openid
	 */
	private String openid;
}