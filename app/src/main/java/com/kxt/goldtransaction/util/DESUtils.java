package com.kxt.goldtransaction.util;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import Decoder.BASE64Decoder;

/**
 * Created by Administrator on 2017/4/17.
 */

public class DESUtils {
    public final  static String SECRETKEY="KH7BJ95FFGF3NGD04824BF80";
    public final  static  String IV="A6MV6780";
    /**
     * 3DES解密
     * @param encryptText 密文
     * @param secretKey 加密时的key
     * @param iv 加密时使用的向量
     * @return 返回解密后的明文
     * @throws Exception
     */
    public static String decrypt(String encryptText,String secretKey,String iv) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

        byte[] decryptData = cipher.doFinal(Base64Utils.decode(encryptText));

        return new String(decryptData, Constart.ENCODE);
    }
    public static String decryptExpen(String encryptText,String secretKey,String iv) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

//        byte[] decordedValue = Base64.decode(encryptText.getBytes(), Base64.DEFAULT);
        byte[] tmpt;
//        tmpt = Base64.decode(encryptText.getBytes("GBK"), Base64.DEFAULT);
        tmpt = encryptText.getBytes("GBK");
        byte[] decryptData = cipher.doFinal(tmpt);

        return new String(decryptData, Constart.ENCODE);
    }



    /**
     * 3DES加密
     * @param plainText 待加密文本
     * @param secretKey 约定的key,至少24位，例如：String secretKey = "zhuxiaojie123@lx100$#365";
     * @param iv 向量 例如： String iv = "12345678";
     * @return 密文
     * @throws Exception : 如果secretKey小于24位，InvalidKeyException
     */
    public static String encrypt(String plainText,String secretKey,String iv) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");//DESede/CBC/PKCS5Padding
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(Constart.ENCODE));
        return Base64Utils.encode(encryptData);
    }
}
