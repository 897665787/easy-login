package com.jqdi.easylogin.core.wx.miniapp.request;

import com.jqdi.easylogin.core.exception.LoginException;
import com.jqdi.easylogin.core.wx.miniapp.model.MaSession;
import com.jqdi.easylogin.core.wx.miniapp.model.MaSessionPhoneNumber;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
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
public class BinarywangMaRequest implements IMaRequest {

	private WxMaService wxMaService;

	public BinarywangMaRequest(String appid, String secret) {
		WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
		config.setAppid(appid);
		config.setSecret(secret);

		wxMaService = new WxMaServiceImpl();
		wxMaService.setWxMaConfig(config);
	}

	/**
	 * <pre>
	 * 特殊使用场景：未登录前端又需要openid、unionid
	 * 官网：https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html
	 * </pre>
	 */
	@Override
	public MaSession getSessionInfo(String code) {
		MaSession maSession = new MaSession();

		// 注意事项：在调用wx.login之后获得的加密数据，才是新的session_key加密的数据
		try {
			/**
			 * 获取sessionKey、openid、unionid
			 */
			WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
			log.debug("WxMaJscode2SessionResult:{}", session);
			
			maSession.setSessionKey(session.getSessionKey());
			maSession.setOpenid(session.getOpenid());
			maSession.setUnionid(session.getUnionid());
		} catch (WxErrorException e) {
			log.error("getSessionInfo error", e);
			WxError error = e.getError();
			throw new LoginException(error.getErrorMsg());
		}
		return maSession;
	}
	
	/**
	 * 获取手机号
	 * 
	 * <pre>
	 * 官网（旧版获取手机号）：https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/deprecatedGetPhoneNumber.html
	 * 
	 * 注意事项：在回调中调用 wx.login 登录，可能会刷新登录态。此时服务器使用 code 换取的 sessionKey 不是加密时使用的 sessionKey，导致解密失败。建议开发者提前进行 login；或者在回调中先使用 checkSession 进行登录态检查，避免 login 刷新登录态。
	 * </pre>
	 */
	@Override
	public MaSessionPhoneNumber getSessionInfoAndPhoneNumber(String encryptedData, String iv, String code) {
		MaSessionPhoneNumber maSessionPhoneNumber = new MaSessionPhoneNumber();
		
		MaSession maSession = getSessionInfo(code);
		maSessionPhoneNumber.setMaSession(maSession);

		WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(maSession.getSessionKey(),
				encryptedData, iv);
		log.debug("WxMaPhoneNumberInfo:{}", phoneNoInfo);
		/**
		 * <pre>
		{
			"phoneNumber": "13580006666",
			"purePhoneNumber": "13580006666",
			"countryCode": "86",
			"watermark": {
				"timestamp": 1614848459,
				"appid": "APPID"
			}
		}
		 * </pre>
		 */
		
		maSessionPhoneNumber.setPhoneNumber(phoneNoInfo.getPhoneNumber());
		return maSessionPhoneNumber;
	}
}
