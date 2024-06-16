package com.jqdi.easylogin.spring.boot.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jqdi.easylogin.core.LoginClient;
import com.jqdi.easylogin.core.ali.miniapp.AlipayMiniappClient;
import com.jqdi.easylogin.core.ali.miniapp.request.AlipayMaRequest;
import com.jqdi.easylogin.core.ali.miniapp.request.IAliMaRequest;
import com.jqdi.easylogin.core.ali.miniappmobile.AlipayMiniappMobileClient;
import com.jqdi.easylogin.core.ali.miniappmobile.request.AlipayMaMobileRequest;
import com.jqdi.easylogin.core.ali.miniappmobile.request.IAliMaMobileRequest;
import com.jqdi.easylogin.core.email.EmailCodeBindClient;
import com.jqdi.easylogin.core.email.EmailCodeClient;
import com.jqdi.easylogin.core.mobile.LocalMobileClient;
import com.jqdi.easylogin.core.mobile.MobileCodeBindClient;
import com.jqdi.easylogin.core.mobile.MobileCodeClient;
import com.jqdi.easylogin.core.mobile.request.AliOneKeyLoginRequest;
import com.jqdi.easylogin.core.mobile.request.ILocalMobileRequest;
import com.jqdi.easylogin.core.password.MobilePasswordClient;
import com.jqdi.easylogin.core.password.UsernamePasswordClient;
import com.jqdi.easylogin.core.repository.PasswordRepository;
import com.jqdi.easylogin.core.repository.VerifycodeRepository;
import com.jqdi.easylogin.core.repository.OauthRepository;
import com.jqdi.easylogin.core.repository.OauthTempRepository;
import com.jqdi.easylogin.core.wx.miniapp.WeixinMiniappClient;
import com.jqdi.easylogin.core.wx.miniapp.request.BinarywangMaRequest;
import com.jqdi.easylogin.core.wx.miniapp.request.IMaRequest;
import com.jqdi.easylogin.core.wx.miniappmobile.WeixinMiniappMobileClient;
import com.jqdi.easylogin.core.wx.miniappmobile.request.BinarywangMaMobileRequest;
import com.jqdi.easylogin.core.wx.miniappmobile.request.IMaMobileRequest;
import com.jqdi.easylogin.core.wx.mp.WeixinAppClient;
import com.jqdi.easylogin.core.wx.mp.WeixinMpClient;
import com.jqdi.easylogin.core.wx.mp.request.BinarywangMpRequest;
import com.jqdi.easylogin.core.wx.mp.request.IMpRequest;
import com.jqdi.easylogin.spring.boot.starter.properties.AlipayMiniappMobileProperties;
import com.jqdi.easylogin.spring.boot.starter.properties.AlipayMiniappProperties;
import com.jqdi.easylogin.spring.boot.starter.properties.LocalMobileProperties;
import com.jqdi.easylogin.spring.boot.starter.properties.WeixinAppProperties;
import com.jqdi.easylogin.spring.boot.starter.properties.WeixinMiniappMobileProperties;
import com.jqdi.easylogin.spring.boot.starter.properties.WeixinMiniappProperties;
import com.jqdi.easylogin.spring.boot.starter.properties.WeixinMpProperties;

@Configuration
@ConditionalOnBean(OauthRepository.class)
@EnableConfigurationProperties({ LocalMobileProperties.class, WeixinAppProperties.class, WeixinMiniappProperties.class,
		WeixinMiniappMobileProperties.class, WeixinMpProperties.class, AlipayMiniappProperties.class,
		AlipayMiniappMobileProperties.class })
public class EasyLoginAutoConfiguration {

	@Bean(LoginType.USERNAME_PASSWORD)
	@ConditionalOnMissingBean(name = LoginType.USERNAME_PASSWORD)
	@ConditionalOnBean(PasswordRepository.class)
	LoginClient usernamePasswordClient(OauthRepository oauthRepository, PasswordRepository passwordRepository) {
		return new UsernamePasswordClient(oauthRepository, passwordRepository);
	}

	@Bean(LoginType.MOBILE_PASSWORD)
	@ConditionalOnMissingBean(name = LoginType.MOBILE_PASSWORD)
	@ConditionalOnBean(PasswordRepository.class)
	LoginClient mobilePasswordClient(OauthRepository oauthRepository, PasswordRepository passwordRepository) {
		return new MobilePasswordClient(oauthRepository, passwordRepository);
	}

	@Bean(LoginType.EMAIL_PASSWORD)
	@ConditionalOnMissingBean(name = LoginType.EMAIL_PASSWORD)
	@ConditionalOnBean(PasswordRepository.class)
	LoginClient emailPasswordClient(OauthRepository oauthRepository, PasswordRepository passwordRepository) {
		return new UsernamePasswordClient(oauthRepository, passwordRepository);
	}

	@Bean(LoginType.LOCAL_MOBILE)
	@ConditionalOnMissingBean(name = LoginType.LOCAL_MOBILE)
	@ConditionalOnProperty(prefix = "easylogin.localMobile", name = "accessKeyId")
	LoginClient localMobileClient(OauthRepository oauthRepository, LocalMobileProperties properties) {
		String accessKeyId = properties.getAccessKeyId();
		String accessKeySecret = properties.getAccessKeySecret();
		String endpoint = properties.getEndpoint();
		ILocalMobileRequest localMobileRequest = new AliOneKeyLoginRequest(accessKeyId, accessKeySecret, endpoint);
		return new LocalMobileClient(oauthRepository, localMobileRequest);
	}

	@Bean(LoginType.MOBILE_CODE)
	@ConditionalOnMissingBean(name = LoginType.MOBILE_CODE)
	@ConditionalOnBean(VerifycodeRepository.class)
	LoginClient mobileCodeClient(OauthRepository oauthRepository, VerifycodeRepository verifycodeRepository) {
		return new MobileCodeClient(oauthRepository, verifycodeRepository);
	}

	@Bean(LoginType.MOBILE_CODE_BIND)
	@ConditionalOnMissingBean(name = LoginType.MOBILE_CODE_BIND)
	@ConditionalOnBean({ OauthTempRepository.class, VerifycodeRepository.class })
	LoginClient mobileCodeBindClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository,
			VerifycodeRepository verifycodeRepository) {
		return new MobileCodeBindClient(oauthRepository, oauthTempRepository, verifycodeRepository);
	}

	@Bean(LoginType.EMAIL_CODE)
	@ConditionalOnMissingBean(name = LoginType.EMAIL_CODE)
	@ConditionalOnBean(VerifycodeRepository.class)
	LoginClient emailCodeClient(OauthRepository oauthRepository, VerifycodeRepository verifycodeRepository) {
		return new EmailCodeClient(oauthRepository, verifycodeRepository);
	}

	@Bean(LoginType.EMAIL_CODE_BIND)
	@ConditionalOnMissingBean(name = LoginType.EMAIL_CODE_BIND)
	@ConditionalOnBean({ OauthTempRepository.class, VerifycodeRepository.class })
	LoginClient emailCodeBindClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository,
			VerifycodeRepository verifycodeRepository) {
		return new EmailCodeBindClient(oauthRepository, oauthTempRepository, verifycodeRepository);
	}

	@Bean(LoginType.WEIXIN_APP)
	@ConditionalOnMissingBean(name = LoginType.WEIXIN_APP)
	@ConditionalOnBean(OauthTempRepository.class)
	@ConditionalOnProperty(prefix = "easylogin.weixinApp", name = "appid")
	LoginClient weixinAppClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository,
			WeixinAppProperties properties) {
		String appid = properties.getAppid();
		String secret = properties.getSecret();
		String token = properties.getToken();
		String aesKey = properties.getAesKey();

		IMpRequest mpRequest = new BinarywangMpRequest(appid, secret, token, aesKey);
		return new WeixinAppClient(oauthRepository, oauthTempRepository, mpRequest);
	}

	@Bean(LoginType.WEIXIN_MINIAPP)
	@ConditionalOnMissingBean(name = LoginType.WEIXIN_MINIAPP)
	@ConditionalOnProperty(prefix = "easylogin.weixinMiniapp", name = "appid")
	LoginClient weixinMiniappClient(OauthRepository oauthRepository, WeixinMiniappProperties properties) {
		String appid = properties.getAppid();
		String secret = properties.getSecret();
		String token = properties.getToken();
		String aesKey = properties.getAesKey();
		String msgDataFormat = properties.getMsgDataFormat();

		IMaRequest maRequest = new BinarywangMaRequest(appid, secret, token, aesKey, msgDataFormat);
		return new WeixinMiniappClient(oauthRepository, maRequest);
	}

	@Bean(LoginType.WEIXIN_MINIAPP_MOBILE)
	@ConditionalOnMissingBean(name = LoginType.WEIXIN_MINIAPP_MOBILE)
	@ConditionalOnProperty(prefix = "easylogin.weixinMiniappMobile", name = "appid")
	LoginClient weixinMiniappMobileClient(OauthRepository oauthRepository, WeixinMiniappMobileProperties properties) {
		String appid = properties.getAppid();
		String secret = properties.getSecret();
		String token = properties.getToken();
		String aesKey = properties.getAesKey();
		String msgDataFormat = properties.getMsgDataFormat();

		IMaMobileRequest maRequest = new BinarywangMaMobileRequest(appid, secret, token, aesKey, msgDataFormat);
		return new WeixinMiniappMobileClient(oauthRepository, maRequest);
	}

	@Bean(LoginType.WEIXIN_MP)
	@ConditionalOnMissingBean(name = LoginType.WEIXIN_MP)
	@ConditionalOnBean(OauthTempRepository.class)
	@ConditionalOnProperty(prefix = "easylogin.weixinMp", name = "appid")
	LoginClient weixinMpClient(OauthRepository oauthRepository, OauthTempRepository oauthTempRepository,
			WeixinMpProperties properties) {
		String appid = properties.getAppid();
		String secret = properties.getSecret();
		String token = properties.getToken();
		String aesKey = properties.getAesKey();

		IMpRequest mpRequest = new BinarywangMpRequest(appid, secret, token, aesKey);
		return new WeixinMpClient(oauthRepository, oauthTempRepository, mpRequest);
	}

	@Bean(LoginType.ALIPAY_MINIAPP)
	@ConditionalOnMissingBean(name = LoginType.ALIPAY_MINIAPP)
	@ConditionalOnProperty(prefix = "easylogin.alipayMiniapp", name = "appid")
	LoginClient alipayMiniappClient(OauthRepository oauthRepository, AlipayMiniappProperties properties) {
		String aesKey = properties.getAesKey();
		String appid = properties.getAppid();
		String privateKey = properties.getPrivateKey();
		String publicKey = properties.getPublicKey();

		IAliMaRequest aliMaRequest = new AlipayMaRequest(aesKey, appid, privateKey, publicKey);
		return new AlipayMiniappClient(oauthRepository, aliMaRequest);
	}

	@Bean(LoginType.ALIPAY_MINIAPP_MOBILE)
	@ConditionalOnMissingBean(name = LoginType.ALIPAY_MINIAPP_MOBILE)
	@ConditionalOnProperty(prefix = "easylogin.alipayMiniappMobile", name = "appid")
	LoginClient alipayMiniappMobileClient(OauthRepository oauthRepository, AlipayMiniappMobileProperties properties) {
		String appid = properties.getAppid();
		String privateKey = properties.getPrivateKey();
		String publicKey = properties.getPublicKey();

		IAliMaMobileRequest aliMaMobileRequest = new AlipayMaMobileRequest(appid, privateKey, publicKey);
		return new AlipayMiniappMobileClient(oauthRepository, aliMaMobileRequest);
	}

}
