package com.ringletter.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	// 生成随机数字和字母,
	public static String getStringRandom(int length) {

		String val = "";
		Random random = new Random();

		// 参数length，表示生成几位随机数
		for (int i = 0; i < length; i++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
		
	
	public static boolean isEmpty(String result){
		
		if(result == null){
			return true;
		}
		
		if("".equals(result)){
			return true ;
		}
		
		
		return false ;
		
	}
	
	// 判断一个字符串是否都为数字  
	public static boolean isDigit(String strNum) {  
	    Pattern pattern = Pattern.compile("[0-9]{1,}");  
	    Matcher matcher = pattern.matcher((CharSequence) strNum);  
	    return matcher.matches();  
	}

}
