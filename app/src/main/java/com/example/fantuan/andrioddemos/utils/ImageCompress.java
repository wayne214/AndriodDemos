package com.example.fantuan.andrioddemos.utils;
import	java.io.FileOutputStream;
import	java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 图片压缩
 * */
public class ImageCompress {
    /**
     * 1.质量压缩
     * 设置bitmap options属性，降低图片质量，像素不减
     * 第一个参数为需要压缩的bitmap图片对象， 第二个参数为压缩后图片保存位置
     * 设置options 属性0-100，来实现压缩（因为png是无损压缩，所以该属性对png是无效的）
     * */

    public static void qualityCompress(Bitmap bmp, File file) {
        // 0-100 100为不压缩
        int quality  = 20;

        ByteArrayOutputStream boss = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bmp.compress(Bitmap.CompressFormat.JPEG, quality, boss);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(boss.toByteArray());
            fos.flush();
            fos.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 2、尺寸压缩（通过缩放图片像素来减少图片占用内存大小）
     * */


    public static void sizeCompress(Bitmap bmp, File file) {
        // 尺寸压缩倍数
        int ratio = 8;
        // 压缩bitmap 到 对应尺寸
        Bitmap result = Bitmap.createBitmap(bmp.getWidth()/ ratio, bmp.getHeight()/ratio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0,0, bmp.getWidth()/ratio,bmp.getHeight() / ratio);
        //将原图画在缩放之后的矩形上
        canvas.drawBitmap(bmp,null, rect, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据放到baos中
        result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 3、采样率压缩（设置图片的采样率，降低图片像素）
     * */

    public static void samplingRateCompress(String filePath, File file) {
        // 数值越高，图片像素越低
        int inSampleSize = 0;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置此参数是仅仅读取图片的宽高到options中，不会将整张图片读到内存中，防止oom
        options.inJustDecodeBounds = true; //为true的时候不会真正加载图片，而是得到图片的宽高信息。

        Bitmap emptyBitmap = BitmapFactory.decodeFile(filePath, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        Bitmap resultBitmap = BitmapFactory.decodeFile(filePath, options);
        ByteArrayOutputStream baos= new ByteArrayOutputStream();

        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            if(file.exists()) {
                file.delete();
            }else {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
