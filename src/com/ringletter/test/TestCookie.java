package com.ringletter.test;

public class TestCookie {
	/**
	 * 
	 * ����վ����get����url�谴��apiҪ��д������ȡ�õ���Ϣ��
	 * 
	 * //���ר�Ÿ����ڵ�������cookie�����ã�Ŀ����Ϊ�˻���û�ѡ��ĳ�����Ϣ
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
			getMethod.setHeader("Cookie", "cy=" + value);// ���ר�Ÿ����ڵ�������cookie�����ã�Ŀ����Ϊ�˻���û�ѡ��ĳ�����Ϣ
			// ����û�������֤��Ϣ
			// client.getCredentialsProvider().setCredentials(
			// new AuthScope(null, -1),
			// new UsernamePasswordCredentials(mUsername, mPassword));
			HttpResponse httpResponse = client.execute(getMethod);
			// statusCode == 200 ����
			statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d(TAG, "statuscode = " + statusCode);
			// �����ص�httpResponse��Ϣ
			if (statusCode == 200) {
				result = retrieveInputStream(httpResponse.getEntity(), charset);
				Cookie cookie;
				String cookname, cookvalue;
				List<Cookie> cookies = client.getCookieStore().getCookies();
				if (cookies.isEmpty()) {
					Log.i(TAG, "-------Cookie NONE---------");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						// ����cookie
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
		} catch (ConnectTimeoutException e) {// ��ʱ���������ӳ���
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

