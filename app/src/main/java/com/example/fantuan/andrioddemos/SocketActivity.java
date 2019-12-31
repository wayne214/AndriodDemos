package com.example.fantuan.andrioddemos;
import	java.io.IOException;
import	java.io.PrintWriter;

import android.app.IntentService;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class SocketActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SocketActivity";

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
//        Socket socket = new Socket("172.16.193.140", 12345);
//        // 2.获取输出流，向服务器端发送消息
//        OutputStream os = socket.getOutputStream();
//        PrintWriter pw = new PrintWriter(os);
//        InetAddress address = InetAddress.getLocalHost();
//        String ip = address.getHostAddress();
//        pw.write("客户端： ~ "+ ip + "~连接服务器！！");
//        pw.flush();
//        socket.shutdownOutput(); // 关闭输出流
//        socket.close();

        /*
        * UDP-client
        * */
        // 1.定义服务器的地址，端口号，数据
        int port = 12345;
        InetAddress server_ip = InetAddress.getByName("172.16.193.140");
        byte[] data = "用户名：admin;密码：123".getBytes();
        // 2.创建数据报，包含发送的数据信息
        DatagramPacket packet = new DatagramPacket(data, data.length, server_ip, port);
        // 3.创建DatagramSocket对象
        DatagramSocket socket1 = new DatagramSocket();
        // 4.想服务器发送消息
        socket1.send(packet);

        /*
        * 接受服务端响应返回的信息
        * */
        // 1.创建数据报， 用于接受服务器端响应的数据
        byte[] data2 = new byte[1024];
        DatagramPacket packet1 = new DatagramPacket(data2, data2.length);
        // 2.接受服务器响应的数据
        socket1.receive(packet1);
        // 3.读取数据
        String reply = new String(data2, 0, packet1.getLength());
//        System.out.println("我是客户端， 服务器说："+ reply);
        Log.i(TAG, "我是客户端， 服务器说：" + reply);
        // 4.关闭资源
        socket1.close();






    }
}
