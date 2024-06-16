package com.jqdi.core.repository;

import com.jqdi.easylogin.core.repository.VerifycodeRepository;

public class CacheVerifycodeRepository implements VerifycodeRepository {

	@Override
	public boolean checkVerifycode(String identifier, String verifyCode) {
		return true;
	}

}