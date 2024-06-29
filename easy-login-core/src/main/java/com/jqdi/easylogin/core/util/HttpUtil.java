package com.jqdi.easylogin.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUtil {
	private static final int CONNECT_TIMEOUT = 5000;
	private static final int READ_TIMEOUT = 5000;

	private HttpUtil() {
	}

	public static String get(String url) {
		try {
			URLConnection connection = new URL(url).openConnection();
			connection.setConnectTimeout(CONNECT_TIMEOUT);
			connection.setReadTimeout(READ_TIMEOUT);

			try (InputStream inputStream = connection.getInputStream()) {
				String response = IOUtils.toString(inputStream, Charset.forName("UTF-8"));
				log.debug("url:{},response:{}", url, response);
				return response;
			}
		} catch (IOException e) {
			log.error("IOException error", e);
		}
		return null;
	}
}
