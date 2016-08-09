package com.supercells.lib;


import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * HTTP
 * @author solo
 *
 */
public class HttpUtil {
	private final static String TAG = "HttpUtil";
	private final static int connect = 30* 1000;
	private final static int timeout = 60*1000;
	/**
	 * get 请求
	 * @param requestUrl URL 地址
	 * @return 结果
     */
	public static String get(String requestUrl) {
		String result = "";
		try {
			Log.d(TAG, "Get方式请求，Request--->" + requestUrl);
			// 新建一个URL对象
			URL url = new URL(requestUrl);
			// 打开一个HttpURLConnection连接
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			// 设置连接主机超时时间
			urlConn.setConnectTimeout(connect);
			//设置从主机读取数据超时
			urlConn.setReadTimeout(timeout);
			// 设置是否使用缓存  默认是true
			urlConn.setUseCaches(true);
			// 设置为Post请求
			urlConn.setRequestMethod("GET");
			//urlConn设置请求头信息
			//设置请求中的媒体类型信息。
			urlConn.setRequestProperty("Content-Type", "application/json");
			//设置客户端与服务连接类型
			urlConn.addRequestProperty("Connection", "Keep-Alive");
			// 开始连接
			urlConn.connect();
			// 判断请求是否成功
			if (urlConn.getResponseCode() == 200) {
				// 获取返回的数据
				result = streamToString(urlConn.getInputStream());
				Log.d(TAG, "Get方式请求成功，result--->" + result);
			} else {
				Log.d(TAG, "Get方式请求失败");
			}
			// 关闭连接
			urlConn.disconnect();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return result;
		}
		return result;
	}

	/**
	 * post 请求
	 * @param baseUrl URL 地址
	 * @param paramsMap 参数
     * @return 结果
     */
	public static String post(String baseUrl,HashMap<String, String> paramsMap) {
		String result = "";
		try {
			//合成参数
			StringBuilder tempParams = new StringBuilder();
			int pos = 0;
			for (String key : paramsMap.keySet()) {
				if (pos > 0) {
					tempParams.append("&");
				}
				tempParams.append(String.format("%s=%s", key,  URLEncoder.encode(paramsMap.get(key),"utf-8")));
				pos++;
			}
			String params =tempParams.toString();
			Log.d(TAG, "post方式请求，Request--->" + baseUrl+"?"+params);
			// 请求的参数转换为byte数组
			byte[] postData = params.getBytes();
			// 新建一个URL对象
			URL url = new URL(baseUrl);
			// 打开一个HttpURLConnection连接
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			// 设置连接超时时间
			urlConn.setConnectTimeout(connect);
			//设置从主机读取数据超时
			urlConn.setReadTimeout(timeout);
			// Post请求必须设置允许输出 默认false
			urlConn.setDoOutput(true);
			//设置请求允许输入 默认是true
			urlConn.setDoInput(true);
			// Post请求不能使用缓存
			urlConn.setUseCaches(false);
			// 设置为Post请求
			urlConn.setRequestMethod("POST");
			//设置本次连接是否自动处理重定向
			urlConn.setInstanceFollowRedirects(true);
			// 配置请求Content-Type
//			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			// 开始连接
			urlConn.connect();
			// 发送请求参数
			DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
			dos.write(postData);
			dos.flush();
			dos.close();
			// 判断请求是否成功
			if (urlConn.getResponseCode() == 200) {
				// 获取返回的数据
				result = streamToString(urlConn.getInputStream());
				Log.d(TAG, "Post方式请求成功，result--->" + result);
			} else {
				Log.d(TAG, "Post方式请求失败");
			}
			// 关闭连接
			urlConn.disconnect();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return result;
		}
		return result;
	}
	/**
	 * 将输入流转换成字符串
	 *
	 * @param is 从网络获取的输入流
	 * @return 字符流
	 */
	private static String streamToString(InputStream is) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			baos.close();
			is.close();
			byte[] byteArray = baos.toByteArray();
			return new String(byteArray);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return null;
		}
	}
}