package com.jqdi.easylogin.core.wx.miniapp.request;

import com.jqdi.easylogin.core.wx.miniapp.model.MaSession;
import com.jqdi.easylogin.core.wx.miniapp.model.MaSessionPhoneNumber;

public interface IMaRequest {

	MaSession getSessionInfo(String code);

	MaSessionPhoneNumber getSessionInfoAndPhoneNumber(String encryptedData, String iv, String code);
}
