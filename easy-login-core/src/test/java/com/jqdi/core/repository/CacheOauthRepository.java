package com.jqdi.core.repository;

import java.util.HashMap;
import java.util.Map;

import com.jqdi.easylogin.core.constants.IdentityType;
import com.jqdi.easylogin.core.repository.OauthRepository;

import lombok.Data;
import lombok.experimental.Accessors;

public class CacheOauthRepository implements OauthRepository {
	int autoIncr = 1;
	Map<CacheKey, CacheValue> cache = new HashMap<>();
	{
		int id = autoIncr++;
		cache.put(new CacheKey().setIdentityType(IdentityType.EMAIL).setIdentifier("6666666@qq.com"),
				new CacheValue().setUserId(String.valueOf(id)).setCertificate(null));
		cache.put(new CacheKey().setIdentityType(IdentityType.MOBILE).setIdentifier("18666666666"),
				new CacheValue().setUserId(String.valueOf(id)).setCertificate(null));
	}

	@Override
	public String getUserId(String identityType, String identifier) {
		CacheKey cacheKey = new CacheKey().setIdentityType(identityType).setIdentifier(identifier);
		CacheValue cacheValue = cache.get(cacheKey);
		if (cacheValue == null) {
			return null;
		}
		return cacheValue.getUserId();
	}

	@Override
	public void bindOauth(String userId, String identityType, String identifier, String certificate) {
		CacheKey cacheKey = new CacheKey().setIdentityType(identityType).setIdentifier(identifier);
		CacheValue cacheValue = cache.get(cacheKey);
		if (cacheValue == null) {
			cacheValue = new CacheValue().setUserId(userId).setCertificate(certificate);
			cache.put(cacheKey, cacheValue);
		}
	}

	@Override
	public String registerUser(String identityType, String identifier, String nickname, String avatar) {
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
		String identityType;
		String identifier;
	}

	@Data
	@Accessors(chain = true)
	public static class CacheValue {
		String userId;
		String certificate;
	}

}