package com.ringletter.test;

public class TestCookie {
	/**
	 * 
	 * 向网站发送get请求，url需按照api要求写，返回取得的信息。
	 * 
	 * //这个专门给大众点评传入cookie参数用，目的是为了获得用户选择的城市信息
	 * 
	 * @param url
	 * 
	 * @param client
	 * 
	 * @return String
	 * 
	 * @author lvqiyong
	 
	public static String getRequest1(String url, DefaultHttpClient client,
	String charset) throws Exception {
		String result = null;
		int statusCode = 0;
		HttpGet getMethod = new HttpGet(url);
		Log.d(TAG, "do the getRequest,url=" + url + "");
		try {
			getMethod.setHeader("User-Agent", USER_AGENT);
			getMethod.setHeader("Cookie", "cy=" + value);// 这个专门给大众点评传入cookie参数用，目的是为了获得用户选择的城市信息
			// 添加用户密码验证信息
			// client.getCredentialsProvider().setCredentials(
			// new AuthScope(null, -1),
			// new UsernamePasswordCredentials(mUsername, mPassword));
			HttpResponse httpResponse = client.execute(getMethod);
			// statusCode == 200 正常
			statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d(TAG, "statuscode = " + statusCode);
			// 处理返回的httpResponse信息
			if (statusCode == 200) {
				result = retrieveInputStream(httpResponse.getEntity(), charset);
				Cookie cookie;
				String cookname, cookvalue;
				List<Cookie> cookies = client.getCookieStore().getCookies();
				if (cookies.isEmpty()) {
					Log.i(TAG, "-------Cookie NONE---------");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						// 保存cookie
						cookie = cookies.get(i);
						cookname = cookie.getName().trim();
						cookvalue = cookie.getValue().trim();
						if (cookname.equals("cy")) {
							name = cookname;
							value = cookvalue;
						}
					}
				}
			} else
				result = "networkerror";
		} catch (ConnectTimeoutException e) {// 超时或网络连接出错
			result = "timeouterror";
			// e.printStackTrace();
		} catch (ClientProtocolException e) {
			result = "networkerror";
			// e.printStackTrace();
		} catch (Exception e) {
			result = "readerror";
			Log.e(TAG, e.getMessage());
			throw new Exception(e);
		} finally {
			getMethod.abort();
		}
		return result;
	}*/
}

