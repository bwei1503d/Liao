package com.ringletter.cipher;

import java.security.MessageDigest;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CipherUtils {

	
	/**
	 * false 失败
	 * @param map
	 * @param sign
	 * @return
	 */
	public static boolean vaildSign(Map<String,String> map,String sign){
		if(getSign(map) == null || "".equals(getSign(map))){
			return false ;
		}		
		if(!sign.equals(getSign(sortString(map)))){
			return false;
		}
		return true ;
	}
	
	
	static final String APP_KEY = "7DFinf08" ; 
	
	public static String getSign(Map<String,String> map){
		String result = "" ;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("appkey=1DLKlkdd&");
			Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				sb.append(entry.getKey()+"=");
				sb.append(entry.getValue()+"&");
			}
			result = sb.toString().substring(0, sb.toString().length()-1);
//					.replaceAll("'","‘" ).replaceAll("\"","“").replaceAll("<", "＜").replace(">", "＞") ;
			System.out.println("laststring : " + result);
			result =  getMD5(result.trim());

		} catch (Exception e) {
			result = null ;
			e.printStackTrace();
		}finally{
			return result.toLowerCase();
		}
	}
	
	/**
	 * 
	 * @param str
	 * @return 所有参数名称  加入
	 */
	public static Map<String,String> sortString(Map<String,String> map){
		Map<String,String> mapResult = new TreeMap<String, String>();
		if(map == null){
			return mapResult;
		}
		if(map.size() ==0){
			return mapResult;
		}
		String[] str = new String[map.size()];
		int i= 0;
		Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			str[i] = entry.getKey();
			i = i +1 ;
		}
		i = 0 ;
		Comparator<Object> comp = Collator.getInstance(java.util.Locale.CHINESE);
		Arrays.sort(str, comp);
		if(str != null && str.length != 0){
			for(int j=0;j<str.length;j++){
				if(!isChineseChar(map.get(str[j]))){
					mapResult.put(str[j], map.get(str[j]));
				}
			}
		}
		return mapResult ;
	}
	
	//判断是否是汉字
	public static boolean isChineseChar(String str) {
		boolean temp = false;
		try {
			if(str == null || "".equals(str) ){
				return false;
			}
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(str);
			if (m.find()) {
                temp = true;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public static String getMD5(String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(content.getBytes());
			return getHashString(digest);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getHashString(MessageDigest digest) {
		StringBuilder builder = new StringBuilder();
		for (byte b : digest.digest()) {
			builder.append(Integer.toHexString((b >> 4) & 0xf));
			builder.append(Integer.toHexString(b & 0xf));
		}
		return builder.toString();
	}
	
}
