package com.example.fantuan.andrioddemos;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;

/**
 * LruCache实现图片缓存
 *
 *
 * */

public class ImageCacheActivity extends AppCompatActivity {
    private LruCache<String, Bitmap> bitmapCache;

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
