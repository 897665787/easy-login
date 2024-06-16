package com.jqdi.easylogin.springbootdemo.repository;

import org.springframework.stereotype.Component;

import com.jqdi.easylogin.core.repository.VerifycodeRepository;

@Component
public class MysqlVerifycodeRepository implements VerifycodeRepository {

	@Override
	public boolean checkVerifycode(String identifier, String verifyCode) {
		return true;
	}

}