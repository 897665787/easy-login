package com.jqdi.easylogin.springbootdemo.repository;

import org.springframework.stereotype.Component;

import com.jqdi.easylogin.core.repository.PasswordRepository;

@Component
public class MysqlPasswordRepository implements PasswordRepository {

	@Override
	public boolean checkPassword(String userId, String password) {
		return true;
	}
}