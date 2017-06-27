package com.ringletter.utils;

/**
 * class ���ã�����һЩ����
 * 
 * @author �˸���
 */
public class Final {
	// ��������ŵĻ� ֻ�軻ǰ�ĸ�
	// company ID
	public static final String CID = "1131170609115540";
	// app ID
	public static final String AID = "dliao";
	// Client ID:
	public static final String CLID = "YXA6IJYxQEzyEeeR5U9CQgptyw";
	// Client Secret
	public static final String CS = "YXA6KcDbLV9Y0VEF93Y0N5Fh5JLeS44";
	// get token URL
	public static final String SURL = "https://a1.easemob.com/" + CID + "/"
			+ AID + "/token";
	// token json
	public static final String TJSON = "{\"grant_type\": \"client_credentials\",\"client_id\": \""
			+ CLID + "\",\"client_secret\":\"" + CS + "\"}";
	// send text URL
	public static final String SENDURL = "https://a1.easemob.com/" + CID + "/"
			+ AID + "/messages";
	// add user Url
	public static final String ADDURL = "https://a1.easemob.com/" + CID + "/"
			+ AID + "/users";
	// update user password_1
	public static final String UPDATEPASSURL1 = "http://a1.easemob.com/" + CID
			+ "/" + AID + "/users/";
	// update user password_2
	public static final String UPDATEPASSURL2 = "/password";

	// head pram
	public static final String CTYPE_KEY = "Content-Type";
	// head pram _value
	public static final String CTYPE_VALUE = "application/json";
	// head pram
	public static final String ACCPET_NAME = "Accept: application/json";
	// head pram _value
	public static final String ACCPET_VALUE = "Keep-Alive";
	// head pram
	public static final String AUTHER = "Authorization";
	// head pram_value_one
	public static final String BEARER = "Bearer";
	// head pram
	public static final String USER_AGENT = "user-agent";
	// head pram _value
	public static final String USER_VALUE = "Mozilla/3.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)";
	// post
	public static final String POST = "POST";
	// put
	public static final String PUT = "PUT";

}
