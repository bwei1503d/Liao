package com.ringletter.cipher;

import java.io.FileInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

public class SecurityUtils {
	/**
	 * 解密
	 * @param cipherText 密文
	 * @return 返回解密后的字符串
	 * @throws Exception 
	 */
	public static String decrypt(String cipherText) throws Exception{
		 // 从文件中得到私钥
    	FileInputStream inPrivate = new FileInputStream(
    			SecurityUtils.class.getClassLoader().getResource("").getPath() + "/rsa_private_key_pkcs8.pem");
        PrivateKey privateKey = RSAUtils.loadPrivateKey(inPrivate);
        byte[] decryptByte = RSAUtils.decryptData(Base64Utils.decode(cipherText), privateKey);
        String decryptStr = new String(decryptByte,"utf-8");
		return decryptStr;
	}
	/**
	 * 加密
	 * @param plainTest 明文
	 * @return	返回加密后的密文
	 * @throws Exception 
	 */
	public static String encrypt(String plainTest) throws Exception{
		FileInputStream inPublic = new FileInputStream(
    			SecurityUtils.class.getClassLoader().getResource("").getPath() + "/rsa_public_key.pem");
        PublicKey publicKey = RSAUtils.loadPublicKey(inPublic);
        // 加密
        byte[] encryptByte = RSAUtils.encryptData(plainTest.getBytes(), publicKey);
        String afterencrypt = Base64Utils.encode(encryptByte);
        return afterencrypt;
	}
}
