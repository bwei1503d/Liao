package com.ringletter.cipher;


/**
 * Created by hongjiang on 16/5/9.
 */
public class JNCryptorUtils {

    public static JNCryptorUtils jnCryptorUtils ;

    private JNCryptorUtils(){

    }

    public  static JNCryptorUtils getInstance(){
        if (jnCryptorUtils == null) {
            jnCryptorUtils = new JNCryptorUtils();
        }
        return jnCryptorUtils;
    }

    public String decryptData(String content, String password){
        String result = "";

        try {
            if(content != null && !"".equals(content)){
                MCrypt mCrypt = new MCrypt(password);
                result = new String(mCrypt.decrypt(MCrypt.bytesToHex(Base64Utils.decode(content)))) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public String encryptData(String content,String password){
        String result = "";
        try {
            MCrypt mCrypt = new MCrypt(password);
            result = Base64Utils.encode(mCrypt.encrypt(content));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return result;
        }
    }



}
