package com.jqdi.easylogin.core.exception;

/**
 * 登录异常
 * 
 * @author JQ棣
 */
public class LoginException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public LoginException(String message){
		super(message);
	}
}
