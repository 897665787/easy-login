package com.jqdi.easylogin.core.ali.miniapp.request;

public interface IAliMaRequest {

	String getMobile(String encryptedData);

	String getUserId(String authcode);

}