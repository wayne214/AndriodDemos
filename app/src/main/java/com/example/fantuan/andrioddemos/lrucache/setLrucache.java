package com.example.fantuan.andrioddemos.lrucache;

import android.graphics.Bitmap;
import android.util.LruCache;
// 设置图片缓存大小
public class setLrucache {
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    final int cacheSize = maxMemory / 8;

    private LruCache<String, Bitmap> memoryCache = new LruCache<String, Bitmap> (cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
//            return super.sizeOf(key, value);
            return bitmap.getByteCount() / 1024;
        }
    };


    public void addBitmapToMemortyCache(String key, Bitmap bitmap) {
        if(getBitmapFromMemCache(key) ==null) {
            memoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return memoryCache.get(key);
    }
}
