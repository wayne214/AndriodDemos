package com.example.fantuan.andrioddemos;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.fantuan.andrioddemos.customView.PieImageView;
import com.example.fantuan.andrioddemos.utils.SquareUtils;
import com.squareup.picasso.Picasso;

/**
 * LruCache实现图片缓存
 *
 *
 * */

public class ImageCacheActivity extends AppCompatActivity {
    private LruCache<String, Bitmap> bitmapCache;
    ImageView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_cache);

        int cacheSize = 20 * 1024 *1024; // 20M
        bitmapCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getAllocationByteCount();
            }
        };

        PieImageView pieImageView = ((PieImageView) findViewById(R.id.pieImageView));
        lv = findViewById(R.id.image_view);


        //        Glide.with(this).load("").into(lv);
//        pieImageView.setProgress(50);


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

    public void addBitmapToCache(String key, Bitmap bitmap){
        if(getBitmapFromCache(key) == null) {
            bitmapCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String key){
        return bitmapCache.get(key);
    }



}
