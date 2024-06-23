package com.jqdi.easylogin.core.wx.miniappmobile.request;

import com.jqdi.easylogin.core.exception.LoginException;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * Binarywang SDK实现
 * 
 * @author JQ棣
 */
@Slf4j
public class BinarywangMaMobileRequest implements IMaMobileRequest {

	private WxMaService wxMaService;

	public BinarywangMaMobileRequest(String appid, String secret, String token, String aesKey, String msgDataFormat) {
		WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
		config.setAppid(appid);
		config.setSecret(secret);
		config.setToken(token);
		config.setAesKey(aesKey);
		config.setMsgDataFormat(msgDataFormat);

		wxMaService = new WxMaServiceImpl();
		wxMaService.setWxMaConfig(config);
	}

	/**
	 * 获取手机号
	 * 
	 * <pre>
	 * 官网（新版获取手机号）：https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/getPhoneNumber.html
	 * </pre>
	 */
	@Override
	public String getMobile(String code) {
		try {
			WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getNewPhoneNoInfo(code);
			log.debug("WxMaPhoneNumberInfo:{}", phoneNoInfo);
			/**
			 * <pre>
			{
				"errcode": 0,
				"errmsg": "ok",
				"phone_info": {
					"phoneNumber": "13580006666",
					"purePhoneNumber": "13580006666",
					"countryCode": 86,
					"watermark": {
						"timestamp": 1614848459,
						"appid": "APPID"
					}
				}
			}
			 * </pre>
			 */
			
			if (phoneNoInfo == null) {
				throw new LoginException("未获取到手机号码");
			}
			return phoneNoInfo.getPhoneNumber();
		} catch (WxErrorException e) {
			log.error("getMobileInfo error", e);
			WxError error = e.getError();
			throw new LoginException(error.getErrorMsg());
		}
	}
}