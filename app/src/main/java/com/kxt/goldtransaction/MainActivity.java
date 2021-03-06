package com.kxt.goldtransaction;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kxt.goldtransaction.bean.LoginBean;
import com.kxt.goldtransaction.bean.XmlOpenBean;
import com.kxt.goldtransaction.util.Base64Utils;
import com.kxt.goldtransaction.util.Ci;
import com.kxt.goldtransaction.util.DESUtils;
import com.kxt.goldtransaction.util.DataPacket;
import com.kxt.goldtransaction.util.MD5Tools;
import com.kxt.goldtransaction.util.RSAUtils;
import com.kxt.goldtransaction.util.ResultJiemiUtil;
import com.kxt.goldtransaction.util.UtilBCD;
import com.kxt.goldtransaction.util.XmlUtil;
import com.socks.library.KLog;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.SocketResponsePacket;


import java.io.IOException;
import java.net.Socket;
import java.security.PublicKey;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    private SocketClient socketClient;
    private String url="117.141.138.101";//交易ip
    private  int prot=41901;//交易端口
    private static String PUCLIC_KEY_TOW ="MIICRzCCAbACCQCEwTrLj/zDfjANBgkqhkiG9w0BAQUFADBoMQswCQYDVQQGEwJDTjELMAkGA1UEChMCSVQxEzARBgNVBAMTCkdlc3NTZXJ2ZXIxEDAOBgNVBAgTB0d1YW5nWGkxEDAOBgNVBAcTB05hbk5pbmcxEzARBgNVBAsTCkdlc3NTZXJ2ZXIwHhcNMTYwOTI3MTA0NjA4WhcNMjYwOTI1MTA0NjA4WjBoMQswCQYDVQQGEwJDTjELMAkGA1UEChMCSVQxEzARBgNVBAMTCkdlc3NTZXJ2ZXIxEDAOBgNVBAgTB0d1YW5nWGkxEDAOBgNVBAcTB05hbk5pbmcxEzARBgNVBAsTCkdlc3NTZXJ2ZXIwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBANzrbkoPp8hIpqlOfuAWVms1f2NxfMnzcyTAo15pGKwlQerYWu8SpWviRDU57K97jfAyxOGApDCjKbMJJh2vPLaSb9VU/OUmwfqpyIKkoW2u3BG3Lr0IBJtosZOw1vfj+zltOZnizRyvnVDj3w+WOIB8jn88glqfmppNYRviavKRAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEAOunehQrYZa9o+sh5qwg2x9rzgZ7EIu8JjiY3IEj2qu4RfuPu2PXbnwA6E+9GrKD3roQX33GmInXVmMlZg/pziYbuHQ3FfRSfjuegSwmWHgnt34LfX63KZoS/4XDTIUOfRoqsXDep1crVptksfvBwdruZ+uEckT8vf7UEAjC2+O8=";

    private static String PUCLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzdaTCixSKYD80y5KihoqHSjrq5a+qqIXsGJmP34RvQ5xC5GhX8dNyzfAgdYyOWhK/Jz699Xw+zNQXhpPVyTKns6dJlRadUiy1YBQ4/dC1zL5imw3QDRqgx+yT/vD7aBGXUww8wiwwOhGVFrXhVvJHjTNaLyoPOekHK1MNUeSK+HOeTVfY9MPcC/6kEDCxTGnjKgZhYmEATYEL6CKBA0+/wAS6iKJ2xnhoSYhfBEqGv2P/7lGrfhRDzG6JjXYR8s/YlGrzsbkqt8vVTNaGpRKo91H6Lfno3a4P0SfH2WX5jlhXhp7hAj9kNP+ryXA2KTb2JKiyN318p3wAgUK28CCZwIDAQAB";
    private String headString="";
    private String BodyXmlString="";
   /* private String BodyXmlString="<?xml  version=\"1.0\" encoding=\"GBK\"?>\n" +
            "<request>\n" +
            "<head>\n" +
            "<h_exch_code>880110</h_exch_code>\n" +
            "<h_bank_no>1111</h_bank_no>\n" +
            "<h_term_type>19</h_term_type>\n"+
            "<h_branch_id>B0077001</h_branch_id>\n"+
            "<h_teller_id>C09100</h_teller_id>\n"+
            "<h_teller_id_1></h_teller_id_1>\n"+
            "<h_teller_id_2></h_teller_id_2>\n"+
            "<h_bk_seq_no>BK3204b43hle9fd723kd84</h_bk_seq_no>\n"+
            "<h_work_date></h_work_date>\n"+
            "<h_exch_date></h_exch_date>" +
            "</head>\n" +
            "<body>\n" +
            "<record>\n" +
            "<branch_id>B0077001</branch_id>\n" +
            "<is_contain_self>1</is_contain_self>\n" +
            "</record>\n" +
            "</body>\n" +
            "</request>";*/
    private String headAndBody="";
   String abcdeStr="";
    byte[] abcdeByte=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText= (TextView) findViewById(R.id.resultText);
//        initData();
//        quitLogin();//退出登录
//        initDataBean();
        initOpenZhuanhu();
        initSocket();
       /* new Thread(new Runnable() {
            @Override
            public void run() {
//                xiongSocke();

            }
        }).start();*/

    }

    private void initOpenZhuanhu() {
        url="113.106.63.156";//开户ip
        prot=47005;//开户端口

        XmlOpenBean xmlOpen =new XmlOpenBean();
        XmlOpenBean.head head =new XmlOpenBean.head();
        head.setH_bank_no("1111");
        head.setH_term_type("19");
        head.setH_exch_code("880110");
        head.setH_branch_id("B0077001");
        head.setH_teller_id("C09100");
        head.setH_teller_id_1("");
        head.setH_teller_id_2("");
        head.setH_bk_seq_no("BK3204b43hle9fd723kd84");
        head.setH_work_date("");
        head.setH_exch_date("");


        XmlOpenBean.body body=new XmlOpenBean.body();
        XmlOpenBean.body.record record=new XmlOpenBean.body.record();
        record.setBranch_id("B0077001");
        record.setIs_contain_self("1");
        body.setRecord(record);
        xmlOpen.setBody(body);
        xmlOpen.setHead(head);


       BodyXmlString= XmlUtil.getStringFormBean(xmlOpen);
        KLog.d(">>>>>>>>>>>",BodyXmlString);
        abcdeByte=BodyXmlString.getBytes();
    }

    private void  initDataBean(){

     /*   UtilBCD utilBcd=new UtilBCD("1","C080","          ");//1100095119
//        Ci.setHuihuaKey("738042001243335486161260");
        LoginBean loginBean=new LoginBean();
        //报文头
        loginBean.setExchCode("C999");
        loginBean.setUserID("1089117276");
        loginBean.setRsp_encrypt_mode("0");
        loginBean.setSerialNo("");
        //报文体
        loginBean.setOper_flag(1);
*/

      //  合约代码查询[C101]

      /*  UtilBCD utilBcd=new UtilBCD("2","C080","1100382617");// A=2 3DES算法（会话密钥）。  会话ID session_id
        Ci.setHuihuaKey("848831292487389350163121");//解密都要 会话密钥 session_key
        LoginBean loginBean=new LoginBean();
        //报文头
        loginBean.setSerialNo("");
        loginBean.setExchCode("C101");
        loginBean.setUserID("1089117276");//必传报文头
        //报文体
        loginBean.setOper_flag(1);
        // "prod_code": "Au99.5", "prod_code": "518800",  "prod_code": "518880",
        loginBean.setProd_code("Au99.5");*/


        //客户登出[C005]
      /*  UtilBCD utilBcd=new UtilBCD("2","C080","1100382617");// A=2 3DES算法（会话密钥）。  会话ID session_id
        Ci.setHuihuaKey("848831292487389350163121");//解密都要 会话密钥 session_key
        LoginBean loginBean=new LoginBean();
        //报文头
        loginBean.setSerialNo("");
        loginBean.setExchCode("C005");
        loginBean.setUserID("1089117276");//必传报文头
        //报文体
        loginBean.setOper_flag(1);*/



        //客户号查询
     /*  UtilBCD utilBcd=new UtilBCD("1","C080","1100411474");// A 为rsa加密   会话ID session_id
        Ci.setHuihuaKey("833654944867503232037648");//解密都要 会话密钥 session_key
        LoginBean loginBean=new LoginBean();
        //报文头
        loginBean.setSerialNo("");
        loginBean.setExchCode("C001");
        loginBean.setUserID("1089117276");//必传报文头
        //报文体
        loginBean.setOper_flag(3);
        loginBean.setMobile_phone("18679039532");*/




        //登录C004
       UtilBCD utilBcd=new UtilBCD("1","C080","          ");
        LoginBean loginBean=new LoginBean();
       //报文头
        loginBean.setExchCode("C004");
        loginBean.setUserID("1089117276");
        //报文体
        loginBean.setOper_flag(1);
        loginBean.setSerialNo("");
        loginBean.setUser_id("1089117276");
        loginBean.setPassword(MD5Tools.MD5("891008"));
        initLoginData(loginBean,utilBcd);
    }

    private void initLoginData(LoginBean loginBean,UtilBCD utilBcd) {

        String jsonE=JSON.toJSONString(loginBean);

        //A区的byte[]
        String  Astr =loginBean.getHeadA(jsonE,utilBcd.getBstr());
        byte[] AByteArry=Astr.getBytes();
        //BCD区的byte[]
        byte[] BCDbyteArry =utilBcd.getBCDByteArray();
        //E区的byte[]
        byte[]  EByteArry =loginBean.getEMIWen();
        abcdeByte=RSAUtils.pingByteA_BCD_E(AByteArry,BCDbyteArry,EByteArry);

        KLog.d("Login",Astr+utilBcd.getBCDString());//json打印
        KLog.json("Login",loginBean.getJsonString());//json打印



    }


    private void xiongSocke() {
        DataPacket d = new DataPacket();
        d.put("SerialNo", "");
        d.put("ExchCode", "C999");
        d.put("UserID", "1089117276");

        d.put("oper_flag", "1");
        d.put("rsp_encrypt_mode", "3");
//        System.out.println(d);
        KLog.json(d.toString());

        StringBuffer buf = new StringBuffer();
        buf.append(d.getHeadA());
        buf.append("0");
        buf.append("C080");
        buf.append("          ");
        buf.append(d.toString());
//        System.out.println(buf.toString());
        KLog.d(buf.toString());

        try {
            Socket socket = null;

                socket = new Socket("117.141.138.101", 41902);

            socket.getOutputStream().write(buf.toString().getBytes());
            socket.getOutputStream().flush();
            byte[] b = new byte[1024];
            socket.getInputStream().read(b);
//            System.out.println(new String(b).trim());
            KLog.d(new String(b).trim());
            int len = Integer.valueOf(new String(Arrays.copyOf(b, 8)));
            Ci c = new Ci();
            byte[] bbb = Arrays.copyOfRange(b, 23,len+8);
//            System.out.println(new String(c.decrypt(bbb),"GBK"));
            KLog.d(new String(c.decrypt(bbb),"GBK"));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出登录
     */
    private void quitLogin() {
        String BBBB="2";
        String CCCC="C080";
        String DDDD="          ";
        String BCD="";
        int  BCDLength=0;
        int  MIWENLength=0;
        String encryStr="";//密文String
        LoginBean  loginBean =new LoginBean();
        loginBean.setOper_flag(1);
        loginBean.setExchCode("C005");
        String loginBeanStr =JSON.toJSONString(loginBean);
        KLog.json(loginBeanStr);
        // 从字符串中得到公钥

        try {
            encryStr= DESUtils.encrypt(loginBeanStr,DESUtils.SECRETKEY,DESUtils.IV);
        } catch (Exception e) {
            e.printStackTrace();
        }

            MIWENLength=Base64Utils.decode(encryStr).length;
            KLog.d("密文的字节长度："+MIWENLength);
            KLog.d(encryStr);


        BCD=BBBB+CCCC+DDDD;
        byte[] sd =Base64Utils.decode(BCD);
        BCDLength=sd.length;
        KLog.d("BCD的字节长度："+BCD+">>>>"+BCDLength);

        int ZJLeng =BCDLength+MIWENLength;


        int ziLeng=String.valueOf(ZJLeng).length();
        KLog.d("总的字节长度："+ZJLeng+"  "+ziLeng);
        String  ziStrig="";
        if(ziLeng<8){

            int l =8-ziLeng;
            for(int f=0;f<l;f++){
                ziStrig+="0";
            }
            ziStrig+=ZJLeng;
            KLog.d("表示的8位数："+ziStrig);
        }
        abcdeStr=ziStrig+BCD+encryStr;

        KLog.d("abcde："+abcdeStr);
    }

    /**
     * 登录测试
     */
    private void initData() {
        String BBBB="1";
        String CCCC="C080";
        String DDDD="          ";
        String BCD="";
        int  BCDLength=0;
        int  MIWENLength=0;
        String encryStr="";//密文String
        LoginBean  loginBean =new LoginBean();
        loginBean.setOper_flag(1);
        loginBean.setUser_id("1089117276");
        loginBean.setExchCode("C004");
        loginBean.setPassword("5ea9144fa6afff4d0559d2f4a6c10eda");
        //判断wifi是否开启
        String ip="";
          ip= "192.168.2.179";
//        loginBean.setLan_ip(ip);
//        loginBean.setWww_ip(ip);
//        loginBean.setRspCode("");
//        loginBean.setRspMsg("");
        loginBean.setSerialNo("");
//        loginBean.setSession_key("");
        loginBean.setUserID("1089117276");
        String loginBeanStr =JSON.toJSONString(loginBean);
        loginBeanStr=loginBeanStr.replace("exchCode","ExchCode");
        loginBeanStr=loginBeanStr.replace("rspCode","RspCode");
        loginBeanStr=loginBeanStr.replace("serialNo","SerialNo");
        loginBeanStr=loginBeanStr.replace("rspMsg","RspMsg");
        loginBeanStr=loginBeanStr.replace("userID","UserID");
        KLog.json(loginBeanStr);

        // 从字符串中得到公钥
        byte[] encryptBytes=null;
        try {
            // 公钥
//            KeyPair keyPair=RSAUtils.generateRSAKeyPair(RSAUtils.DEFAULT_KEY_SIZE);
//            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();//自动生成key
//            PublicKey publicKey=RSAUtils.loadPublicKey(PUCLIC_KEY);//字符串取生成key
//           PublicKey publicKey =RSAUtils.loadPublicKey(PUCLIC_KEY);//字符串取生成key2


            // 从文件中得到公钥
            InputStream jjjj=this.getClass().getClassLoader().getResourceAsStream("assets/"+"rsa_pubic_C080.pem");
            PublicKey publicKey = RSAUtils.loadPublicKey(jjjj);

           RSAUtils.printPublicKeyInfo(publicKey);
//            byte[] encryptBytes=    RSAUtils.encryptByPublicKey(loginBeanStr.getBytes(),publicKey.getEncoded());//直接加密
             encryptBytes=RSAUtils.encryptData(loginBeanStr.getBytes("GBK"),publicKey);//分成100字节一个个加密
            MIWENLength=encryptBytes.length;
            KLog.d("密文的字节长度："+MIWENLength);
//            KLog.d(encryStr);
/*
            // 从文件中得到公钥
            InputStream ppppp=this.getClass().getClassLoader().getResourceAsStream("assets/"+"rsa_private_C080.pem");
            PrivateKey privateKey = RSAUtils.loadPrivateKey(ppppp);
           byte[] jiemso  = RSAUtils.deCodeData(encryptBytes,privateKey);
            KLog.d(Base64Utils.encode(jiemso));*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        BCD=BBBB+CCCC+DDDD;
        byte[] sd = null;
        sd = BCD.getBytes();

        abcdeByte =RSAUtils.pingByte(sd,encryptBytes);

        BCDLength=abcdeByte.length;
        KLog.d("BCDE的字节长度："+">>>>"+BCDLength);

        int ZJLeng =BCDLength;


       int ziLeng=String.valueOf(BCDLength).length();
        String  ziStrig="";
        if(ziLeng<8){

            int l =8-ziLeng;
            for(int f=0;f<l;f++){
                ziStrig+="0";
            }
            ziStrig+=ZJLeng;
            KLog.d("表示的8位数："+ziStrig);
        }
        abcdeStr=ziStrig+BCD+encryStr;
        abcdeByte=RSAUtils.pingByte(ziStrig.getBytes(),abcdeByte);

        KLog.d("abcde："+abcdeStr);

    }
    private void initSocket() {
        socketClient = new SocketClient(url, prot);
        socketClient.registerSocketDelegate(new SocketClient.SocketDelegate(){
            @Override
            public void onConnected(SocketClient client) {
                Log.d("socketTO","onConnected.......");
                    socketClient.send(abcdeByte);
            }

            @Override
            public void onDisconnected(SocketClient client) {
                
                
                
                Log.d("socketTO","onDisconnected.......");
                String error = client.getState().toString();
            }

            @Override
            public void onResponse(SocketClient client, @NonNull SocketResponsePacket responsePacket) {
                KLog.d("socketTO","onResponse.......");
                String responseMsg = responsePacket.getMessage();
                KLog.d("socketTO",responseMsg);

               try {


                   ResultJiemiUtil resuUtil =new ResultJiemiUtil(responseMsg,responsePacket.getData());
                  String EstrJson = resuUtil.JiemiGo();
                   KLog.json("socketTO",EstrJson);

                   XmlOpenBean jjjj =XmlUtil.getStringFormBean(responseMsg);
                   KLog.json(JSON.toJSONString(jjjj));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        socketClient.setConnectionTimeout(1000 * 15);
//        socketClient.setHeartBeatInterval(1000);//害我三秒掉线
//        socketClient.setRemoteNoReplyAliveTimeout(1000 *5);//害我五秒掉线
        socketClient.setCharsetName("GBK");
        socketClient.connect();
    }
    
    
}
