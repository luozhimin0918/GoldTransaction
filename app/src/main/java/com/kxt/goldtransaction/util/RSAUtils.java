package com.kxt.goldtransaction.util;


import java.io.ByteArrayOutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.io.InputStream;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.crypto.Cipher;

import static com.kxt.goldtransaction.util.Constart.RSA;
import static com.kxt.goldtransaction.util.Constart.RSAPAD;

/**
 * Created by Administrator on 2017/4/13.
 */

public class RSAUtils {
    public static final int DEFAULT_KEY_SIZE = 2048;//秘钥默认长度

    /**
     * 打印公钥信息
     *
     * @param publicKey
     */
    public static void printPublicKeyInfo(PublicKey publicKey)
    {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        System.out.println("----------RSAPublicKey----------");
        System.out.println("PublicKeyString: "+Base64Utils.encode(rsaPublicKey.getEncoded()));
        System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());
        System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
        System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
    }

    public static void printPrivateKeyInfo(PrivateKey privateKey)
    {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        System.out.println("----------RSAPrivateKey ----------");
        System.out.println("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());
        System.out.println("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
        System.out.println("PrivatecExponent=" + rsaPrivateKey.getPrivateExponent().toString());

    }
    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048
     *                  一般1024
     * @return
     */
    public static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 用公钥对字符串进行加密
     *
     * @param data 原文
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        // 得到公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PublicKey keyPublic = kf.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance(Constart.RSAPAD);
        cp.init(Cipher.ENCRYPT_MODE, keyPublic);
        return cp.doFinal(data);
    }

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
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
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
     * 从文件中输入流中加载公钥
     *
     * @param in
     *            公钥输入流
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(InputStream in) throws Exception
    {
        try
        {
            return loadPublicKey(readKey(in));
        } catch (IOException e)
        {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e)
        {
            throw new Exception("公钥输入流为空");
        }
    }
    /**
     * 读取密钥信息
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static String readKey(InputStream in) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null)
        {
            if (readLine.charAt(0) == '-')
            {
                continue;
            } else
            {
                sb.append(readLine);
                sb.append('\r');
            }
        }

        return sb.toString();
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
    public static byte[] encryptData(byte[] data, PublicKey publicKey) throws IOException,GeneralSecurityException
    {
        try
        {
            Cipher cipher = Cipher.getInstance(RSAPAD);
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            ByteArrayOutputStream  byteArrayOutputStream =new ByteArrayOutputStream();
            for(int i=0;i<data.length;i=i+100){
                int len=0;
                if((i+100)>data.length){
                     len=data.length-i;
                }else {
                    len=100;
                }
                byte[] tmp=new byte[len];
                System.arraycopy(data,i,tmp,0,tmp.length);
                byteArrayOutputStream.write(cipher.doFinal(tmp));
            }
            // 传入编码数据并返回编码结果
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 用私钥解密 <br>
     * 由于RSA算法对加密的内容长度有限制，本系统采取每100位长度加密一次，
     * 得到的结果为128位密文
     * ，然后将128位的密文拼在一起，形成最终的密文。
     * 解析时需要每取128位密文解密，然后将解密的内容拼在一起。
     *
     *            需加密数据的byte数据
     *            私钥
     * @return 解密后的byte型数据
     */
    public static byte[] deCodeData(byte[] src, PrivateKey privateKey) throws IOException,GeneralSecurityException
    {
        try
        {
            Cipher cipher = Cipher.getInstance(RSAPAD);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
            for(int i=0;i<src.length;i=i+128){
                byte[] temp =new byte[128];
                System.arraycopy(src,i,temp,0,temp.length);
                byteArrayOutputStream.write( cipher.doFinal(temp));
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e)
        {
            return null;
        }
    }
    /**
     * 从文件中加载私钥
     *
     *            私钥文件名
     * @return 是否成功
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(InputStream in) throws Exception
    {
        try
        {
            return loadPrivateKey(readKey(in));
        } catch (IOException e)
        {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e)
        {
            throw new Exception("私钥输入流为空");
        }
    }
    /**
     * 从字符串中加载私钥<br>
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception
    {
        try
        {
            byte[] buffer = Base64Utils.decode(privateKeyStr);
            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e)
        {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e)
        {
            throw new Exception("私钥非法");
        } catch (NullPointerException e)
        {
            throw new Exception("私钥数据为空");
        }
    }
}
