package com.jqdi.easylogin.core.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtil {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private static ObjectMapper mapper = new ObjectMapper();

	static {
		// 忽略null值
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		// 时间类型数据格式化
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 遇未知属性不报错
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 允许使用非双引号属性名字
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// 允许一个没有属性的类被序列化
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	private JsonUtil() {
	}

	public static String toJsonString(Object object) {
		if (object == null) {
			return null;
		}
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			logger.error("json process error", e);
		}
		return null;
	}

	public static <T> T toEntity(String jsonString, Class<T> clazz) {
		if (jsonString == null) {
			return null;
		}
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (JsonParseException e) {
			logger.error("json parse error", e);
		} catch (JsonMappingException e) {
			logger.error("json mapping error", e);
		} catch (IOException e) {
			logger.error("io error", e);
		}
		return null;
	}

	public static <T> List<T> toList(String jsonString, Class<T> parameterClasses) {
		if (jsonString == null) {
			return Collections.emptyList();
		}
		JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, parameterClasses);
		try {
			return mapper.readValue(jsonString, javaType);
		} catch (JsonParseException e) {
			logger.error("json parse error", e);
		} catch (JsonMappingException e) {
			logger.error("json mapping error", e);
		} catch (IOException e) {
			logger.error("io exception error", e);
		}
		return Collections.emptyList();
	}

	public static boolean isJsonString(String string) {
		if (StringUtils.isBlank(string)) {
			return false;
		}
		try {
			mapper.readTree(string);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static JsonNode toJsonNode(String jsonString) {
		if (jsonString == null) {
			return null;
		}
		try {
			return mapper.readTree(jsonString);
		} catch (IOException e) {
			logger.error("io exception error", e);
		}
		return null;
	}
	
	public static ObjectMapper mapper() {
		return mapper;
	}
}
