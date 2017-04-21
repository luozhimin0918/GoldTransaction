package com.kxt.goldtransaction.util;

import com.socks.library.KLog;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/4/21.
 */

public class ResultJiemiUtil {

    private  byte[] result;
    private String  responseMsg;
    private String Bstr;

    public ResultJiemiUtil(String responseMsg,byte[] result) {

        this.result=result;
        this.responseMsg=responseMsg;
        this.Bstr=responseMsg.substring(8,9);
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
                break;
            case "0":
                break;

        }

        return jiemiStr;
    }

}
