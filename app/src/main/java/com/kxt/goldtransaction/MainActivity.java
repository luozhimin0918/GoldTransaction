package com.kxt.goldtransaction;

import android.net.wifi.WifiInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.kxt.goldtransaction.bean.LoginBean;
import com.kxt.goldtransaction.util.NetUtils;
import com.kxt.goldtransaction.util.RSAUtils;
import com.socks.library.KLog;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.SocketResponsePacket;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    private SocketClient socketClient;
    private String url="117.141.138.101";
    private  int prot=41901;
    private static String PUCLIC_KEY_TOW =

                            "MIICRzCCAbACCQCEwTrLj/zDfjANBgkqhkiG9w0BAQUFADBoMQswCQYDVQQGEwJD" +"\r"+
                            "TjELMAkGA1UEChMCSVQxEzARBgNVBAMTCkdlc3NTZXJ2ZXIxEDAOBgNVBAgTB0d1" +"\r"+
                            "YW5nWGkxEDAOBgNVBAcTB05hbk5pbmcxEzARBgNVBAsTCkdlc3NTZXJ2ZXIwHhcN" +"\r"+
                            "MTYwOTI3MTA0NjA4WhcNMjYwOTI1MTA0NjA4WjBoMQswCQYDVQQGEwJDTjELMAkG" +"\r"+
                            "A1UEChMCSVQxEzARBgNVBAMTCkdlc3NTZXJ2ZXIxEDAOBgNVBAgTB0d1YW5nWGkx" +"\r"+
                            "EDAOBgNVBAcTB05hbk5pbmcxEzARBgNVBAsTCkdlc3NTZXJ2ZXIwgZ8wDQYJKoZI" +"\r"+
                            "hvcNAQEBBQADgY0AMIGJAoGBANzrbkoPp8hIpqlOfuAWVms1f2NxfMnzcyTAo15p" +"\r"+
                            "GKwlQerYWu8SpWviRDU57K97jfAyxOGApDCjKbMJJh2vPLaSb9VU/OUmwfqpyIKk" +"\r"+
                            "oW2u3BG3Lr0IBJtosZOw1vfj+zltOZnizRyvnVDj3w+WOIB8jn88glqfmppNYRvi" +"\r"+
                            "avKRAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEAOunehQrYZa9o+sh5qwg2x9rzgZ7E" +"\r"+
                            "Iu8JjiY3IEj2qu4RfuPu2PXbnwA6E+9GrKD3roQX33GmInXVmMlZg/pziYbuHQ3F" +"\r"+
                            "fRSfjuegSwmWHgnt34LfX63KZoS/4XDTIUOfRoqsXDep1crVptksfvBwdruZ+uEc" +"\r"+
                            "kT8vf7UEAjC2+O8=";
    private static String PUCLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfRTdcPIH10gT9f31rQuIInLwe"
            + "7fl2dtEJ93gTmjE9c2H+kLVENWgECiJVQ5sonQNfwToMKdO0b3Olf4pgBKeLThra"
            + "z/L3nYJYlbqjHC3jTjUnZc0luumpXGsox62+PuSGBlfb8zJO6hix4GV/vhyQVCpG"
            + "9aYqgE7zyTRZYX9byQIDAQAB" ;

    String aaaaa="";
    String bbbbb="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText= (TextView) findViewById(R.id.resultText);
        initData();
        initSocket();
    }

    private void initData() {
        LoginBean  loginBean =new LoginBean();
        loginBean.setOper_flag(1);
        loginBean.setUser_id("1089117276");
        loginBean.setPassword("5ea9144fa6afff4d0559d2f4a6c10eda");
        //判断wifi是否开启
        String ip="";
          ip= NetUtils.getLocalIPAddress();
        loginBean.setLan_ip(ip);
        KLog.json(JSON.toJSONString(loginBean));
        // 从字符串中得到公钥
        try {
            PublicKey publicKey = RSAUtils.loadPublicKey(PUCLIC_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initSocket() {
        socketClient = new SocketClient(url, prot);
        socketClient.registerSocketDelegate(new SocketClient.SocketDelegate(){
            @Override
            public void onConnected(SocketClient client) {
                Log.d("socketTO","onConnected.......");
//                socketClient.send("hello, server !--------------------------->Android");
//                socketClient.setHeartBeatMessage("hello, server !--------------------------->Android");
            }

            @Override
            public void onDisconnected(SocketClient client) {
                
                
                
                Log.d("socketTO","onDisconnected.......");
                String error = client.getState().toString();
            }

            @Override
            public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket) {
                Log.d("socketTO","onResponse.......");
                String responseMsg = responsePacket.getMessage();
                Log.d("socketTO",responseMsg);
            }
        });
        socketClient.setConnectionTimeout(1000 * 15);
//        socketClient.setHeartBeatInterval(1000);//害我三秒掉线
//        socketClient.setRemoteNoReplyAliveTimeout(1000 *5);//害我五秒掉线
        socketClient.setCharsetName("GBK");
        socketClient.connect();
    }
    
    
}
