package com.kxt.goldtransaction.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2017/4/21.
 */

public class UtilBCD {
    private  String BCDString;//BCD的字符串
    private String Bstr;
    private String Cstr;
    private String Dstr;

    public String getBCDString() {
        return BCDString;
    }

    public UtilBCD(String Bstr, String Cstr, String Dstr) {
        this.Bstr=Bstr;
        this.Cstr=Cstr;
        this.Dstr=Dstr;
        BCDString=Bstr+Cstr+Dstr;
    }
    public byte[] getBCDByteArray(){
        byte[] bcdByte=BCDString.getBytes();
        return bcdByte;
    }

    public void setBCDString(String BCDString) {
        this.BCDString = BCDString;
    }

    public String getBstr() {
        return Bstr;
    }

    public void setBstr(String bstr) {
        Bstr = bstr;
    }

    public String getCstr() {
        return Cstr;
    }

    public void setCstr(String cstr) {
        Cstr = cstr;
    }

    public String getDstr() {
        return Dstr;
    }

    public void setDstr(String dstr) {
        Dstr = dstr;
    }
}
