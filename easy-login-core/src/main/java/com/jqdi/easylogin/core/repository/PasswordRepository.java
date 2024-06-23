package com.jqdi.easylogin.core.repository;

/**
 * 密码存储
 * 
 * @author JQ棣
 */
public interface PasswordRepository {
	/**
	 * 检查密码
	 * 
	 * @param userId
	 *            用户ID
	 * @param password
	 *            密码
	 * @return
	 */
	boolean checkPassword(String userId, String password);
}