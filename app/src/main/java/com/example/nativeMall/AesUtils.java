package com.example.nativeMall;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AesUtils {
	/**  
     * 加密  
     *   
     * @param content 需要加密的内容  
     * @param password  加密密码  
     * @return  
     */    
    public static String encrypt(String content, String password) {    
        StringBuffer sb = new StringBuffer();   
        try {           
            KeyGenerator kgen = KeyGenerator.getInstance("AES");    
            kgen.init(128, new SecureRandom(password.getBytes()));    
            SecretKey secretKey = kgen.generateKey();    
            byte[] enCodeFormat = secretKey.getEncoded();    
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");    
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器     
            byte[] byteContent = content.getBytes("utf-8");    
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化     
            byte[] result = cipher.doFinal(byteContent);    
            //将10进制字节数组转化为16进制字符串  
            for (int i = 0; i < result.length; i++) {    
                String hex = Integer.toHexString(result[i] & 0xFF);    
                if (hex.length() == 1) {    
                        hex = '0' + hex;    
                }    
                sb.append(hex.toUpperCase());    
            }   
           return sb.toString(); // 加密     
        } catch (NoSuchAlgorithmException e) {    
                e.printStackTrace();    
        } catch (NoSuchPaddingException e) {    
                e.printStackTrace();    
        } catch (InvalidKeyException e) {    
                e.printStackTrace();    
        } catch (UnsupportedEncodingException e) {    
                e.printStackTrace();    
        } catch (IllegalBlockSizeException e) {    
                e.printStackTrace();    
        } catch (BadPaddingException e) {    
                e.printStackTrace();    
        }    
        return null;    
    }
    
    /**解密  
     * @param content  待解密内容  
     * @param password 解密密钥  
     * @return  
     */    
    public static String decrypt(String content, String password) {   
        try {  
	        //将16进制字符创转为10进制字节数组  
	         byte[] result = new byte[content.length()/2];  
	         for (int i = 0;i< content.length()/2; i++) {    
	            int high = Integer.parseInt(content.substring(i*2, i*2+1), 16);    
	            int low = Integer.parseInt(content.substring(i*2+1, i*2+2), 16);    
	            result[i] = (byte) (high * 16 + low);    
	         }  
	         KeyGenerator kgen = KeyGenerator.getInstance("AES");    
	         kgen.init(128, new SecureRandom(password.getBytes()));    
	         SecretKey secretKey = kgen.generateKey();    
	         byte[] enCodeFormat = secretKey.getEncoded();    
	         SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");                
	         Cipher cipher = Cipher.getInstance("AES");// 创建密码器     
	         cipher.init(Cipher.DECRYPT_MODE, key);// 初始化     
	         byte[] data = cipher.doFinal(result);   
            return new String(data); // 解密    
        } catch (NoSuchAlgorithmException e) {    
                e.printStackTrace();    
        } catch (NoSuchPaddingException e) {    
                e.printStackTrace();    
        } catch (InvalidKeyException e) {    
                e.printStackTrace();    
        } catch (IllegalBlockSizeException e) {    
                e.printStackTrace();    
        } catch (BadPaddingException e) {    
                e.printStackTrace();    
        }    
        return null;    
    }
}
