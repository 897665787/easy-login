package com.jqdi.easylogin.core.ali.miniappmobile.request;

import com.jqdi.easylogin.core.ali.miniappmobile.model.AliMobileUserId;

public interface IAliMaMobileRequest {

	AliMobileUserId getPhoneNumber(String code);

}
