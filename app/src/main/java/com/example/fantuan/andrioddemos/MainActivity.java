package com.example.fantuan.andrioddemos;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.fantuan.andrioddemos.adapters.MyAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    private RecyclerView myRecylerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyAdapter adapter;
    private List<String> datas = new ArrayList<>();;

    private SmartRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initView();
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setOnLoadMoreListener(this);

        myRecylerView = findViewById(R.id.my_recycler_view);
        myRecylerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        myRecylerView.setLayoutManager(layoutManager);


        adapter = new MyAdapter(datas);

        myRecylerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

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

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        datas.addAll(datas);
        swipeRefreshLayout.finishLoadMore();

        adapter.notifyDataSetChanged();
    }
}
