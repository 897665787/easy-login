package com.jqdi.easylogin.core.repository;

public interface VerifycodeRepository {
	/**
	 * 检查验证码
	 * 
	 * @param identifier
	 * @param verifycode
	 * @return
	 */
	boolean checkVerifycode(String identifier, String verifycode);
}