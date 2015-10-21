package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

	public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					connection.setDoInput(true);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder sBuilder = new StringBuilder();
					String line = "";
					while (null != (line = reader.readLine())) {
						sBuilder.append(line);
					}
					if (null != listener) {
						// 回调onFinish()方法
						listener.onFinish(sBuilder.toString());
					}
				} catch (Exception e) {
					if (null != listener) {
						// 回调onError()方法
						listener.onError(e);
					}
				} finally {
					if (null != connection) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
