package com.example.fantuan.andrioddemos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fantuan.andrioddemos.customView.PieImageView;
import com.example.fantuan.andrioddemos.utils.SquareUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";
    public final int MSG_DOWN_FAIL = 1;
    public final int MSG_DOWN_SUCCESS = 2;
    public final int MSG_DOWN_START = 3;

//    @BindView(R.id.pieImageView) PieImageView pieImageView;
//    @OnClick(R.id.pieImageView) void click() {
//        Toast.makeText(this, "单击了吗？", Toast.LENGTH_SHORT).show();
//    }

    ImageView lv;
    Bitmap reuseBitmap;

    @BindView(R.id.btn_start_thread)
    Button btnStart;

    @BindView(R.id.tv_status)
    TextView tvShow;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DOWN_START:
                    tvShow.setText("down start");
                    break;
                case MSG_DOWN_SUCCESS:
                    tvShow.setText("down success");
                    break;
                case MSG_DOWN_FAIL:
                    tvShow.setText("down fail");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_thread:
                new MyThread().start();
                break;
            default:
                break;
        }
    }

    class MyThread extends Thread {

        @Override
        public void run() {
            handler.sendEmptyMessage(MSG_DOWN_START);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Message msg = Message.obtain();
            msg.what = MSG_DOWN_SUCCESS;
            handler.sendMessage(msg);

        }
    }


}
