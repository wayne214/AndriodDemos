package com.example.fantuan.andrioddemos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;

public class RegionBitmapActivity extends AppCompatActivity {


    @BindView(R.id.region_image) ImageView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region_bitmap);
        lv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                showRegionImage();
            }
        });
    }


    private void showRegionImage(){
        try {
            // 获取图片宽高
            InputStream inputStream = getAssets().open("fireworks.png");
            BitmapFactory.Options temOptions = new BitmapFactory.Options();
            temOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream,null, temOptions);
            int with = temOptions.outWidth;
            int height = temOptions.outHeight;
            // 设置显示图片中心区域
            BitmapRegionDecoder regionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = regionDecoder.decodeRegion(new Rect(0, 0, 100, 100), options);

            lv.setImageBitmap(bitmap);
        }catch (IOException e){}
    }
}
