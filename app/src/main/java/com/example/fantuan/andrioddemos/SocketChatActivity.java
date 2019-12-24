package com.example.fantuan.andrioddemos;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketChatActivity extends AppCompatActivity implements Runnable{
    private TextView txtshow;
    private EditText editsend;
    private Button btnsend;
    private static final String HOST= "172.16.193.140";
    private static final int PORT = 12345;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out= null;
    private String content= "";
    private StringBuilder sb = null;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0x123) {
                sb.append(content);
                txtshow.setText(sb.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_chat);
        sb = new StringBuilder();
        txtshow = findViewById(R.id.txtshow);
        editsend = findViewById(R.id.editsend);
        btnsend = findViewById(R.id.btnsend);


        new Thread(){
            @Override
            public void run() {
                try{
                    socket = new Socket(HOST,PORT);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String msg = editsend.getText().toString();
                new Thread() {
                    @Override
                    public void run() {
                        if(socket.isConnected()){
                            if(!socket.isOutputShutdown()) {
                                out.println(msg);
                            }
                        }
                    }
                }.start();

            }
        });

        new Thread(SocketChatActivity.this).start();
    }

    @Override
    public void run() {
        try{
            while (true){
                if(socket !=null && socket.isConnected()) {
                    if (socket !=null && !socket.isInputShutdown()) {
                        if(in !=null && (content = in.readLine()) != null) {
                            content += "\n";
                            handler.sendEmptyMessage(0x123);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
