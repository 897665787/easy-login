package com.jqdi.easylogin.core.qq.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenOpenid {
	String accessToken;
	String openid;// 官方文档说参数need_openid可以传回openid，不确定
}
