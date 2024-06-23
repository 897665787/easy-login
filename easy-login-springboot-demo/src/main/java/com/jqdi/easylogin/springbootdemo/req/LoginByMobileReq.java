package com.jqdi.easylogin.springbootdemo.req;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginByMobileReq {
	@NotBlank(message = "手机号不能为空") 
	String mobile;
	
	@NotBlank(message = "验证码不能为空") 
	String code;
	
	@NotBlank(message = "绑定码不能为空") 
	String bindCode;

}
