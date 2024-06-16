package com.jqdi.core.repository;

import java.util.HashMap;
import java.util.Map;

import com.jqdi.easylogin.core.enums.IdentityType;
import com.jqdi.easylogin.core.repository.OauthRepository;

import lombok.Data;
import lombok.experimental.Accessors;

public class CacheOauthRepository implements OauthRepository {
	int autoIncr = 1;
	Map<CacheKey, CacheValue> cache = new HashMap<>();

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