package com.example.fantuan.andrioddemos;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fantuan.andrioddemos.customView.PieImageView;
import com.example.fantuan.andrioddemos.utils.SquareUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.pieImageView) PieImageView pieImageView;
//    @OnClick(R.id.pieImageView) void click() {
//        Toast.makeText(this, "单击了吗？", Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        PieImageView pieImageView = ((PieImageView) findViewById(R.id.pieImageView));
//        pieImageView.setProgress(50);

        ImageView lv = findViewById(R.id.image_view);


        SquareUtils.ProgressListener listener = new SquareUtils.ProgressListener() {
            @Override
            public void update(int percent) {
                Log.i("okhttp测试下载图片", "update: "+percent);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pieImageView.setProgress(percent);
                    }
                });
            }
        };
        Picasso picasso = SquareUtils.getPicasso(this, listener);

        picasso.load("https://cdn.pixabay.com/photo/2016/04/25/18/07/halcyon-1352522_1280.jpg")
                .placeholder(R.mipmap.ic_launcher)
                .config(Bitmap.Config.ARGB_4444)
                .into(lv);
    }

}
