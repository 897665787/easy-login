package com.jqdi.easylogin.spring.boot.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@ConfigurationProperties(prefix = "easylogin.weixin-mp")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeixinMpProperties {
	String appid;
	String secret;
	String token;
	String aesKey;
}
