package com.jqdi.easylogin.core.qq.request.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MeResp {
	/**
	 * 错误码，0代表成功（错误码：https://wiki.connect.qq.com/%e5%85%ac%e5%85%b1%e8%bf%94%e5%9b%9e%e7%a0%81%e8%af%b4%e6%98%8e）
	 */
	@JsonProperty("error")
	private Integer error;
	/**
	 * 错误信息 
	 */
	@JsonProperty("error_description")
	private String errorDescription;
	
	/**
	 * appid
	 */
	@JsonProperty("client_id")
	private String clientId;
	/**
	 * openid
	 */
	private String openid;
}