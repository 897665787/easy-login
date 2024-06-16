package com.jqdi.easylogin.core.repository;

public interface PasswordRepository {
	/**
	 * 检查密码
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	boolean checkPassword(String userId, String password);
}