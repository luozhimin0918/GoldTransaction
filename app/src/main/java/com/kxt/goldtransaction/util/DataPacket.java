package com.kxt.goldtransaction.util;

/**
 * Created by Administrator on 2017/4/20.
 */

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class DataPacket {
    private Map<String, String> map;

    public DataPacket() {
        map = new HashMap<>();
    }

    public void put (String key, String value){
        map.put(key, value);
    }

    public String get(String key){
        return map.get(key);
    }

    public String getHeadA(){
        int l = this.toJSON().length + 15;
        return String.format("%08d", l);
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("{");
        for(Map.Entry<String, String> entry : map.entrySet()){
            buf.append("\"");
            buf.append(entry.getKey());
            buf.append("\":");
            buf.append("\"");
            buf.append(entry.getValue());
            buf.append("\",");
        }
        buf.deleteCharAt(buf.length()-1);
        buf.append("}");
        return buf.toString();
    }

    public byte[] toJSON(){
        try {
            byte[] res =  this.toString().getBytes("GBK");
            return res;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
