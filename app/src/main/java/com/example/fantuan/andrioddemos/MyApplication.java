package com.example.fantuan.andrioddemos;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApplication extends Application{
    private static Context mContext;
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        Log.i(TAG, "onCreate: ");
    }

    public static Context getInstance() {
        return mContext;
    }
}
