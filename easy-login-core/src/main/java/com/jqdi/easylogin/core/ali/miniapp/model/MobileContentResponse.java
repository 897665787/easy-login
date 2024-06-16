package com.jqdi.easylogin.core.ali.miniapp.model;

import com.alipay.api.AlipayResponse;

public class MobileContentResponse extends AlipayResponse {
	private static final long serialVersionUID = 1541197123190567551L;

	private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
