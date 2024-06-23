package com.jqdi.easylogin.springbootdemo.req;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginByWeixinAppReq {
	@NotBlank(message = "微信授权码不能为空") 
	String wxcode;
}
