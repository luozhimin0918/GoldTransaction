package com.kxt.goldtransaction.util;

import com.socks.library.KLog;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/4/21.
 */

public class ResultJiemiUtil {

    private  byte[] result;
    private String  responseMsg;
    private String Bstr;
    private String Estr;

    public ResultJiemiUtil(String responseMsg,byte[] result) {

        this.result=result;
        this.responseMsg=responseMsg;
        this.Bstr=responseMsg.substring(8,9);
        this.Estr=this.responseMsg.substring(23);
    }
    public String JiemiGo(){
        String jiemiStr="";
        switch (this.Bstr){
            case "3":

                try {
                    int len = Integer.valueOf(new String(Arrays.copyOf(this.result, 8)));
                    Ci c = new Ci();
                    byte[] bbb = Arrays.copyOfRange(this.result, 23,len+8);
                    jiemiStr=new String(c.decrypt(bbb),"GBK");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case "1":

                try {
                    InputStream jjjj=this.getClass().getClassLoader().getResourceAsStream("assets/"+"rsa_private_C0805.pem");
                    PrivateKey publicKey = RSAUtils.loadPrivateKey(jjjj);
                    byte[]  priByteArr=RSAUtils.deCodeData(Estr.getBytes(),publicKey);//分成100字节一个个加密
                    jiemiStr=new String(priByteArr,"GBK");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "0":
                jiemiStr=Estr;
                break;

        }

        return jiemiStr;
    }

}
