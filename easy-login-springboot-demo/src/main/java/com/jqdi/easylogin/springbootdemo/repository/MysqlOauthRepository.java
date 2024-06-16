package com.jqdi.easylogin.springbootdemo.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jqdi.easylogin.core.enums.IdentityType;
import com.jqdi.easylogin.core.repository.OauthRepository;

import lombok.Data;
import lombok.experimental.Accessors;

@Component
public class MysqlOauthRepository implements OauthRepository {
	/**
	 * 模拟表结构存储
	 * 
	 * <pre>
	CREATE TABLE `bu_user_oauth` (
	  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
	  `user_id` int(11) NOT NULL COMMENT 'bu_user_info.id',
	  `identity_type` varchar(32) NOT NULL COMMENT '账号类型(mobile:手机号,wx-unionid:微信unionid,wx-openid-miniapp:微信小程序openid,wx-openid-mp:微信公众号openid,email:邮箱,username:用户名,sina:新浪微博,qq:QQ)',
	  `identifier` varchar(32) NOT NULL COMMENT '手机号、邮箱、用户名或第三方应用的唯一标识',
	  `certificate` varchar(255) DEFAULT NULL COMMENT '凭证(站内的保存密码，站外的不保存或保存token)',
	  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
	  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	  PRIMARY KEY (`id`) USING BTREE,
	  UNIQUE KEY `uniq_ui_it` (`user_id`,`identity_type`),
	  UNIQUE KEY `uniq_identifier_it` (`identifier`,`identity_type`)
	) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户认证表';
	 * </pre>
	 */
	Map<CacheKey, CacheValue> cache = new HashMap<>();

	int autoIncr = 1;
	
	@Override
	public String getUserId(IdentityType identityType, String identifier) {
		CacheKey cacheKey = new CacheKey().setIdentityType(identityType).setIdentifier(identifier);
		CacheValue cacheValue = cache.get(cacheKey);
		if (cacheValue == null) {
			return null;
		}
		return cacheValue.getUserId();
	}

	@Override
	public void bindOauth(String userId, IdentityType identityType, String identifier, String certificate) {
		CacheKey cacheKey = new CacheKey().setIdentityType(identityType).setIdentifier(identifier);
		CacheValue cacheValue = cache.get(cacheKey);
		if (cacheValue == null) {
			cacheValue = new CacheValue().setUserId(userId).setCertificate(certificate);
			cache.put(cacheKey, cacheValue);
		}
	}

	@Override
	public String registerUser(IdentityType identityType, String identifier, String nickname, String avatar) {
		CacheKey cacheKey = new CacheKey().setIdentityType(identityType).setIdentifier(identifier);
		CacheValue cacheValue = cache.get(cacheKey);
		if (cacheValue == null) {
			cacheValue = new CacheValue().setUserId(String.valueOf(autoIncr++)).setCertificate(null);
			cache.put(cacheKey, cacheValue);
		}
		return cacheValue.getUserId();
	}

	@Data
	@Accessors(chain = true)
	public static class CacheKey {
		IdentityType identityType;
		String identifier;
	}

	@Data
	@Accessors(chain = true)
	public static class CacheValue {
		String userId;
		String certificate;
	}

}