package com.example.fantuan.andrioddemos;
import java.io.IOException;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fantuan.andrioddemos.adapters.MyAdapter;
import com.example.fantuan.andrioddemos.bean.BannerBean;
import com.example.fantuan.andrioddemos.utils.GlideImageLoader;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;


import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    private final String TAG = "MainActivity";

    private RecyclerView myRecylerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyAdapter adapter;
    private List<String> datas = new ArrayList<>();;

    private SmartRefreshLayout swipeRefreshLayout;

    private Banner banner;

    private List<String> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initView();
        getData();
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://www.wanandroid.com/banner/json")
                            .build();
                    Response response = null;
                    response = client.newCall(request).execute();

                    client.newCall(request).enqueue(new Callback() {

                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            Gson gson = new Gson();
                            BannerBean bean1 = gson.fromJson(result, BannerBean.class);
                            Log.d(TAG, "response: "+ result);
                            Log.d(TAG, "bannerBean: "+ bean1.getData().get(0).getTitle());

                            List<BannerBean.DataBean> data1 = bean1.getData();
                            for (int i = 0; i < data1.size() ; i++) {
                                String imagePath1 = data1.get(i).getImagePath();
                                imageList.add(imagePath1);
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    banner.setImages(imageList);
                                    banner.start();
                                }
                            });
                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        // 下拉刷新
        swipeRefreshLayout.setOnRefreshListener(this);
        // 上拉加载
        swipeRefreshLayout.setOnLoadMoreListener(this);

        myRecylerView = findViewById(R.id.my_recycler_view);
        myRecylerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        myRecylerView.setLayoutManager(layoutManager);


        adapter = new MyAdapter(datas);

        myRecylerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

        banner = (Banner) findViewById(R.id.header_banner);
        banner.setImageLoader(new GlideImageLoader());
        Log.i(TAG, "initView: banner"+ imageList);


    }

    private void initDatas() {
        for (int i = 0; i < 50; i++) {
            datas.add("数据"+i);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(MainActivity.this,"Click:"+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        adapter.delateData(position);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        datas.clear();
        initDatas();
        swipeRefreshLayout.finishRefresh();
        // 通知数据刷新
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        datas.addAll(datas);
        swipeRefreshLayout.finishLoadMore();

        adapter.notifyDataSetChanged();
    }
}
