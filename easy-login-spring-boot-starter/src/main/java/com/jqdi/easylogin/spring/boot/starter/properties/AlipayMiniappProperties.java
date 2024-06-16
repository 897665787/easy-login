package com.jqdi.easylogin.spring.boot.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@ConfigurationProperties(prefix = "easylogin.alipay-miniapp")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlipayMiniappProperties {
	String appid;
	String privateKey;
	String publicKey;
	String aesKey;
}
