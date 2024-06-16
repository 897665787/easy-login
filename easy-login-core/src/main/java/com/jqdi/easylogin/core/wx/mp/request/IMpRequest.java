package com.jqdi.easylogin.core.wx.mp.request;

import com.jqdi.easylogin.core.wx.mp.model.MpAccessToken;
import com.jqdi.easylogin.core.wx.mp.model.MpUserInfo;

public interface IMpRequest {

	MpAccessToken getAccessToken(String code);

	MpUserInfo getUserinfo(String accessToken, String openid);
}