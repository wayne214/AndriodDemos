package com.example.fantuan.andrioddemos;
import	java.io.IOException;
import	java.io.PrintWriter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Thread(){
            @Override
            public void run() {
                try {
                    acceptServer();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void acceptServer() throws IOException{
        // 1.创建客户端Socket, 指定服务器地址和端口
        Socket socket = new Socket("172.16.193.140", 12345);
        // 2.获取输出流，向服务器端发送消息
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        pw.write("客户端： ~ "+ ip + "~连接服务器！！");
        pw.flush();
        socket.shutdownOutput(); // 关闭输出流
        socket.close();
    }
}
