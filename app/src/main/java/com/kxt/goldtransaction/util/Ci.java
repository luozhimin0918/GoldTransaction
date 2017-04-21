package com.kxt.goldtransaction.util;

import android.util.Base64;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2017/4/20.
 */

public class Ci {
    private IvParameterSpec iv;
    private Cipher des;
    private SecretKey deskey;
    private   String key;
    private  String ivStr;
    private static String huihuaKey;

    public Ci() {

        this.key ="KH7BJ95FFGF3NGD04824BF80";
        this.ivStr="A6MV6780";
        try {
            des = Cipher.getInstance("desede/CBC/PKCS5Padding");
            iv = new IvParameterSpec(ivStr.getBytes());
            deskey = new SecretKeySpec(key.getBytes(), "desede");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Ci(String keyTem) {

        this.key =huihuaKey;
        this.ivStr="A6MV6780";
        try {
            des = Cipher.getInstance("desede/CBC/PKCS5Padding");
            iv = new IvParameterSpec(ivStr.getBytes());
            deskey = new SecretKeySpec(this.key.getBytes(), "desede");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] encrypt( byte[] M) {
        return op(Cipher.ENCRYPT_MODE, M);
    }

    public byte[] decrypt( byte[] C) {
        return op(Cipher.DECRYPT_MODE, C);
    }

    private byte[] op(int mode, byte[] data) {
        try {
            des.init(mode, deskey, iv);
            return des.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getHuihuaKey() {
        return huihuaKey;
    }

    public static void setHuihuaKey(String huihuaKey) {
        Ci.huihuaKey = huihuaKey;
    }
}
