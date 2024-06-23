package com.jqdi.easylogin.core.repository;

/**
 * 验证码存储
 * 
 * @author JQ棣
 */
public interface VerifycodeRepository {
	/**
	 * 检查验证码
	 * 
	 * @param identifier
	 *            手机号、邮箱、用户名或第三方应用的唯一标识
	 * @param verifycode
	 *            验证码
	 * @return
	 */
	boolean checkVerifycode(String identifier, String verifycode);
}