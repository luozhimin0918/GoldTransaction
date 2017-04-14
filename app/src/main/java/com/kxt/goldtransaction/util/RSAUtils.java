package com.kxt.goldtransaction.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by Administrator on 2017/4/13.
 */

public class RSAUtils {
    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr
     *            公钥数据字符串
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(String publicKeyStr) throws Exception
    {
        try
        {
            byte[] buffer = Base64Utils.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(Constart.RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e)
        {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e)
        {
            throw new Exception("公钥非法");
        } catch (NullPointerException e)
        {
            throw new Exception("公钥数据为空");
        }
    }


    /**
     * 用公钥加密 <br>
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data
     *            需加密数据的byte数据
     *            公钥
     * @return 加密后的byte型数据
     */
    public static byte[] encryptData(byte[] data, PublicKey publicKey)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(Constart.RSA);
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            ByteBufferOutputStream  byteBufferOutputStream=new ByteBufferOutputStream();
            for(int i=0;i<data.length;i=i+100){
                int len=0;
                if((i+100)>data.length){
                     len=data.length-i;
                }else {
                    len=100;
                }
                byte[] tmp=new byte[len];
                System.arraycopy(data,i,tmp,0,tmp.length);
                byteBufferOutputStream.write(cipher.doFinal(tmp));
            }
            // 传入编码数据并返回编码结果
            return byteBufferOutputStream.toString().getBytes();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
