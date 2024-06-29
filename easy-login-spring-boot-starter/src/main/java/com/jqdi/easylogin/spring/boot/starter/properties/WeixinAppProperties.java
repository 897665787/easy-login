package com.jqdi.easylogin.spring.boot.starter.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@ConfigurationProperties(prefix = "easylogin.weixin-app")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeixinAppProperties {
	String appid;
	String secret;
}
