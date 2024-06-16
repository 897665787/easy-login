package com.jqdi.easylogin.springbootdemo.resp;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResp {
	/** 需要绑定账号 */
	Boolean needBind;
	
	/** 绑定码（needBind=true时有值） */
	String bindCode;

	/** token值（needBind=false时有值） */
	String token;
}
