package com.kxt.goldtransaction;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.SocketResponsePacket;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    private SocketClient socketClient;
    private String url="117.141.138.101";
    private  int prot=41901;
    String aaaaa="";
    String bbbbb="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText= (TextView) findViewById(R.id.resultText);
        initSocket();
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
        socketClient.setHeartBeatInterval(1000);
        socketClient.setRemoteNoReplyAliveTimeout(1000 *5);
        socketClient.setCharsetName("GBK");
        socketClient.connect();
    }
}
