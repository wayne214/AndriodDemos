package com.example.fantuan.andrioddemos.utils;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.WorkerThread;

import com.example.fantuan.andrioddemos.MyApplication;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class SquareUtils {
    static private OkHttpClient client;

    static public synchronized OkHttpClient getClient() {
        if(client == null) {
            final File cacheDir = MyApplication.getInstance().getExternalCacheDir();
            client = new OkHttpClient.Builder()
                    .cache(new Cache(new File(cacheDir, "okhttp"), 60*1024*1024))
                    .build();
        }

        return client;
    }

    private static OkHttpClient getProgressBarClien(final ProgressListener listener){
        return getClient().newBuilder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                        .body(new ProgressResponseBody(originalResponse.body(), listener))
                        .build();
            }
        }).build();
    }

    static public Picasso getPicasso(Context context, ProgressListener listener){
        OkHttpClient client = getProgressBarClien(listener);
        OkHttp3Downloader downloader = new OkHttp3Downloader(client);
        return new Picasso.Builder(context).downloader(downloader)
                .memoryCache(com.squareup.picasso.Cache.NONE)
                .build();
    }


    public interface ProgressListener{
        @WorkerThread
        void update(@IntRange(from = 0, to = 100) int percent);
    }

    private static class ProgressResponseBody extends ResponseBody{
        private final ResponseBody responseBody;
        private final ProgressListener progressListener;
        private BufferedSource bufferedSource;


        public ProgressResponseBody(ResponseBody responseBody, ProgressListener progressListener) {
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if(bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;
                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    totalBytesRead +=bytesRead != -1 ? bytesRead : 0;
                    if(progressListener !=null) {
                        progressListener.update((int)((100* totalBytesRead)/responseBody.contentLength()));
                    }
                    return bytesRead;
                }
            };
        }
    }

}
