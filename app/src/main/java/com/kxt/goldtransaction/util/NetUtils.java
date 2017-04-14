package com.kxt.goldtransaction.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

/**
 * Created by Administrator on 2017/4/14.
 */

public class NetUtils {
    // 获取本地IP函数

    public static String getLocalIPAddress() {

        try {

            for (Enumeration<NetworkInterface> mEnumeration = NetworkInterface

                    .getNetworkInterfaces(); mEnumeration.hasMoreElements();) {

                NetworkInterface intf = mEnumeration.nextElement();

                for (Enumeration<InetAddress> enumIPAddr = intf

                        .getInetAddresses(); enumIPAddr.hasMoreElements();) {

                    InetAddress inetAddress = enumIPAddr.nextElement();

                    // 如果不是回环地址

                    if (!inetAddress.isLoopbackAddress()) {

                        // 直接返回本地IP地址

                        return inetAddress.getHostAddress().toString();

                    }

                }

            }

        } catch (SocketException ex) {

            System.err.print("error");

        }

        return null;

    }


   /* public static String GetNetIp()

    {

        URL infoUrl = null;

        InputStream inStream = null;

        try

        {

            //http://iframe.ip138.com/ic.asp

            //infoUrl = new URL("http://city.ip138.com/city0.asp");

            try {
                infoUrl = new URL("http://iframe.ip138.com/ic.asp");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            URLConnection connection = infoUrl.openConnection();

            HttpURLConnection httpConnection = (HttpURLConnection)connection;

            int responseCode = 0;
            try {
                responseCode = httpConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(responseCode == HttpURLConnection.HTTP_OK)

            {

                inStream = httpConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"utf-8"));

                StringBuilder strber = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null)

                    strber.append(line + "\n");

                inStream.close();

                //从反馈的结果中提取出IP地址

                int start = strber.indexOf("[");

                int end = strber.indexOf("]", start + 1);

                line = strber.substring(start + 1, end);

                return line;

            }

        }

        catch(MalformedURLException e) {

            e.printStackTrace();

        }

        catch (IOException e) {

            e.printStackTrace();

        }

        return null;

    }*/
}
