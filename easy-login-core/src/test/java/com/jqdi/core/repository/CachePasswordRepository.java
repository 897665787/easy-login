package com.jqdi.core.repository;

import com.jqdi.easylogin.core.repository.PasswordRepository;

public class CachePasswordRepository implements PasswordRepository {

	@Override
	public boolean checkPassword(String userId, String password) {
		return true;
	}

}