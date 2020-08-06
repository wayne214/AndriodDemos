package com.example.fantuan.andrioddemos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;

public class BitmapPoolActivity extends AppCompatActivity {
    private final String TAG = "BitmapPoolActivity";

    int resIndex;
    int[] resIds = {R.mipmap.beauty, R.mipmap.logo};

    @BindView(R.id.pool_image) ImageView lv;

    Bitmap reuseBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_pool);

        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        options.inSampleSize = 2;
        options.inMutable = true;

        reuseBitmap = BitmapFactory.decodeResource(getResources(), resIds[0], options);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty, options);
        //getAllocationByteCount获取bitmap占用的字节大小
        Log.i(TAG, "bitmap width is : " + bitmap.getWidth());
        Log.i(TAG, "bitmap height is : " + bitmap.getHeight());
        Log.i(TAG, "bitmap size is : " + bitmap.getAllocationByteCount());
        Log.i(TAG, "bitmap size is : " + bitmap.getByteCount());
    }

    /**
     * 复用 inBitmap 之前，需要调用 canUseForInBitmap 方法来判断 reuseBitmap 是否可以被复用。这是因为 Bitmap 的复用有一定的限制：

     在 Android 4.4 版本之前，只能重用相同大小的 Bitmap 内存区域；
     4.4 之后你可以重用任何 Bitmap 的内存区域，只要这块内存比将要分配内存的 bitmap 大就可以。
     * */
    public static boolean canUseForInBitmap(Bitmap candidate, BitmapFactory.Options targetOptions) {
        int width = targetOptions.outWidth / Math.max(targetOptions.inSampleSize, 1);
        int heitht = targetOptions.outHeight / Math.max(targetOptions.inSampleSize, 1);
        int byteCount = width * heitht * getBytesPerPixel(candidate.getConfig());
        return byteCount <= candidate.getAllocationByteCount();
    }

    private static int getBytesPerPixel(Bitmap.Config config) {
        int bytesPerPixel;
        switch (config) {
            case ALPHA_8:
                bytesPerPixel = 1;
                break;
            case RGB_565:
            case ARGB_4444:
                bytesPerPixel = 2;
                break;
            default:
                bytesPerPixel = 4;
                break;
        }
        return bytesPerPixel;
    }

    private Bitmap getBitmap() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), resIds[resIndex % 2], options);
        options.inJustDecodeBounds = false;
        if(canUseForInBitmap(reuseBitmap, options)){
            options.inMutable = true;
            options.inBitmap = reuseBitmap;
        }

        return BitmapFactory.decodeResource(getResources(), resIds[resIndex++ % 2], options);
    }

    public void onClick(View view) {
        lv.setImageBitmap(getBitmap());
    }
}
